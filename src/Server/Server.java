package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

import Packets.Packet;
import Packets.PacketHandler;
import Packets.PacketManager;

public final class Server {
    
    private final int port;
    private final ServerHandler serverHandler;
    private final PacketHandler packetHandler;
    
    public Server(int port, ServerHandler serverHandler, PacketHandler packetHandler) {
        this.port = port;
        this.serverHandler = serverHandler;
        this.packetHandler = packetHandler;
        new ConnectionListener().start();
    }
    
    private final class ConnectionListener extends Thread {
        @Override
        public void run() {
            System.out.println("Listening on port " + port + ".");
            try (ServerSocket serverSock = new ServerSocket(port)) {
                while (true) {
                    new ClientServerCommunicationHandler(serverSock.accept()).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private final class ClientServerCommunicationHandler extends Thread {
        
        private final Socket socket;
        
        public ClientServerCommunicationHandler(Socket socket) {
            this.socket = socket;
        }
        
        @Override
        public void run() {
            String address = socket.getInetAddress().getHostAddress();
            int port = socket.getPort();
            System.out.println("Client connection from (" + address + ":" + port + ").");
            serverHandler.onConnection();
            try (DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
                processClient(in);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Client terminated from (" + address + ":" + port + ").");
                serverHandler.onDisconnection();
            }
        }
        
        private void processClient(DataInputStream in) throws IOException {
            while (true) {
                final Packet p = PacketManager.get(in);
                Method[] methods = packetHandler.getClass().getDeclaredMethods();
                for (final Method method : methods) {
                    if (method.getName().equals("on" + p.getName())) {
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    method.invoke(p);
                                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.run();
                        break;
                    }
                }
            }
        }
    }
}
