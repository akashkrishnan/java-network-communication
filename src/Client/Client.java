package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

import Packets.Packet;
import Packets.PacketHandler;
import Packets.PacketManager;

public final class Client {
    
    private final String host;
    private final int port;
    private final ClientHandler clientHandler;
    private final PacketHandler packetHandler;
    
    public Client(String host, int port, ClientHandler clientHandler, PacketHandler packetHandler) {
        this.host = host;
        this.port = port;
        this.clientHandler = clientHandler;
        this.packetHandler = packetHandler;
        new ClientServerCommunicationHandler().start();
    }
    
    private final class ClientServerCommunicationHandler extends Thread {
        
        private Socket socket;
        
        @Override
        public void run() {
            while (true) {
                System.out.println("Connecting to " + host + " on port " + port + ".");
                try {
                    socket = new Socket(host, port);
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    System.out.println("Connection to server has been established.");
                    clientHandler.onConnection();
                    processServer(in);
                } catch (Exception ex) {
                    System.out.println("Connection to server has been lost.");
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    clientHandler.onDisconnection();
                }
            }
        }
        
        private void processServer(DataInputStream in) throws IOException {
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
