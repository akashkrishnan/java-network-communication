/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Packets.Packet;
import Packets.PacketHandler;
import Packets.PacketManager;
import Utilities.DataConversion;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 *
 * @author akashkrishnan@aakay.net
 */
public final class Client {

    private String HOST;
    private int PORT;
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private ClientHandler clientHandler;
    private PacketHandler packetHandler;
    private PacketManager packetManager;
    private ServerListener serverListener;

    public Client(String host, int port) {
        this.HOST = host;
        this.PORT = port;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public void setPacketHandler(PacketHandler packetHandler) {
        this.packetHandler = packetHandler;
    }

    public void setPacketManager(PacketManager packetManager) {
        this.packetManager = packetManager;
    }

    public boolean start() {
        if (this.serverListener == null
                && this.clientHandler != null
                && this.packetHandler != null
                && this.packetManager != null) {
            this.serverListener = new ServerListener();
            ((Thread) this.serverListener).start();
            return true;
        }
        return false;
    }

    public void sendPacket(Packet p) {
        try {
            this.out.write(p.getBytes());
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private final class ServerListener extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("Connecting to " + HOST + " on port " + PORT + ".");
                    Client.this.socket = new Socket(HOST, PORT);
                    Client.this.in = socket.getInputStream();
                    Client.this.out = socket.getOutputStream();
                    System.out.println("Connection to server has been established.");
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                try {
                    Client.this.clientHandler.handleConnection();
                    byte[] headerBytes = new byte[Packet.HEADER_SIZE];
                    byte[] sizeBytes = new byte[4];
                    byte[] data = new byte[0];
                    int header = 0;
                    int size = 0;
                    int bin;
                    int i = 0;
                    int j = 0;
                    while (true) {
                        if ((bin = Client.this.in.read()) != -1) {
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
                                                            method.invoke(packetHandler, packetManager.getPacketInstance(_header, _data));
                                                        } catch (Exception ex) {
                                                            System.out.println(ex);
                                                            Client.this.clientHandler.handlePacket(_header, _size, _data);
                                                        }
                                                    }
                                                }.run();
                                                found = true;
                                                break;
                                            }
                                        }
                                        if (!found) {
                                            Client.this.clientHandler.handlePacket(header, size, data);
                                        }
                                    } catch (Exception ex) {
                                        System.out.println(ex);
                                        Client.this.clientHandler.handlePacket(header, size, data);
                                    }
                                    i = 0;
                                    j = 0;
                                }
                            }
                        } else {
                            System.out.println("Connection terminated.");
                            Client.this.clientHandler.handleDisconnection();
                            break;
                        }
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                    System.out.println("Connection terminated.");
                    Client.this.clientHandler.handleDisconnection();
                }
                System.out.println("Reconnecting in 5 seconds.");
                try {
                    Thread.sleep(5000);
                } catch (Exception ex) {
                    System.out.println(ex);
                    System.out.println("Fatal error has occurred. Exiting.");
                    System.exit(-1);
                    break;
                }
            }
        }
    }
}
