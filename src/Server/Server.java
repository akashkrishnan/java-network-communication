/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Packets.Packet;
import Packets.PacketHandler;
import Packets.PacketManager;
import Utilities.DataConversion;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author akashkrishnan@aakay.net
 */
public final class Server<CC extends ConnectionClient, CCL extends ConnectionClientList> {

    private int PORT;
    protected CCL ccl;
    private Class<CC> ccc;
    private ServerHandler serverHandler;
    private PacketHandler packetHandler;
    private PacketManager packetManager;
    private ConnectionListener connectionListener;

    public Server(int port, Class<CC> ccc, Class<CCL> cclc) {
        try {
            PORT = port;
            this.ccc = ccc;
            ccl = (CCL) cclc.newInstance();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void setServerHandler(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    public void setPacketHandler(PacketHandler packetHandler) {
        this.packetHandler = packetHandler;
    }

    public void setPacketManager(PacketManager packetManager) {
        this.packetManager = packetManager;
    }

    public boolean start() {
        if (this.connectionListener == null
                && this.serverHandler != null
                && this.packetHandler != null
                && this.packetManager != null) {
            this.connectionListener = new ConnectionListener();
            ((Thread) this.connectionListener).start();
            return true;
        }
        return false;
    }

    private final class ConnectionListener extends Thread {

        @Override
        public void run() {
            try {
                System.out.println("Listening on port " + PORT + ".");
                ServerSocket serverSock = new ServerSocket(PORT);
                while (true) {
                    ServerCommunicationHandler serverCommunicationHandler = new ServerCommunicationHandler(serverSock.accept());
                    ((Thread) serverCommunicationHandler).start();
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    private final class ServerCommunicationHandler extends Thread {

        private Socket socket;
        private CC cc;
        private InputStream in;
        private OutputStream out;

        public ServerCommunicationHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                String address = this.socket.getInetAddress().getHostAddress();
                int port = this.socket.getPort();

                this.in = this.socket.getInputStream();
                this.out = this.socket.getOutputStream();

                this.cc = (CC) Server.this.ccc.newInstance();
                this.cc.setAddress(address);
                this.cc.setPort(port);
                this.cc.setOutputStream(this.out);

                Server.this.ccl.add(this.cc);
                System.out.println("Client connection from (" + address + ":" + port + ")");
                processClient();
                System.out.println("Client terminated from (" + address + ":" + port + ")");
                Server.this.ccl.remove(address, port);

                this.out.close();
                this.in.close();
                this.socket.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }

        private void processClient() {
            try {
                Server.this.serverHandler.handleConnection(cc);
                byte[] headerBytes = new byte[Packet.HEADER_SIZE];
                byte[] sizeBytes = new byte[4];
                byte[] data = new byte[0];
                int header = 0;
                int size = 0;
                int bin;
                int i = 0;
                int j = 0;
                while (true) {
                    if ((bin = this.in.read()) != -1) {
                        byte b = (byte) bin;
                        if (i < headerBytes.length) {
                            headerBytes[j] = b;
                            header = DataConversion.toInt(headerBytes);
                            i++;
                            j++;
                            if (j == headerBytes.length) {
                                j = 0;
                            }
                        } else if (i < headerBytes.length + sizeBytes.length) {
                            sizeBytes[j] = b;
                            size = DataConversion.toInt(sizeBytes);
                            data = new byte[size];
                            i++;
                            j++;
                            if (j == sizeBytes.length) {
                                j = 0;
                            }
                        } else if (i < headerBytes.length + sizeBytes.length + data.length) {
                            data[j] = b;
                            i++;
                            j++;
                            if (j == data.length) {
                                try {
                                    boolean found = false;
                                    Method[] methods = packetHandler.getClass().getDeclaredMethods();
                                    for (final Method method : methods) {
                                        if (method.getName().equals("handle" + packetManager.getPacketName(header))) {
                                            final int _header = header;
                                            final int _size = size;
                                            final byte[] _data = data;
                                            new Runnable() {

                                                @Override
                                                public void run() {
                                                    try {
                                                        method.invoke(packetHandler, packetManager.getPacketInstance(_header, _data), cc);
                                                    } catch (Exception ex) {
                                                        System.out.println(ex);
                                                        Server.this.serverHandler.handlePacket(_header, _size, _data, cc);
                                                    }
                                                }
                                            }.run();
                                            found = true;
                                            break;
                                        }
                                    }
                                    if (!found) {
                                        Server.this.serverHandler.handlePacket(header, size, data, cc);
                                    }
                                } catch (Exception ex) {
                                    System.out.println(ex);
                                    Server.this.serverHandler.handlePacket(header, size, data, cc);
                                }
                                i = 0;
                                j = 0;
                            }
                        }
                    } else {
                        Server.this.serverHandler.handleDisconnection(cc);
                        break;
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
                Server.this.serverHandler.handleDisconnection(cc);
            }
        }
    }
}
