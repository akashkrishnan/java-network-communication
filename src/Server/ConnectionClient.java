/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Packets.Packet;
import java.io.OutputStream;

/**
 *
 * @author akashkrishnan@aakay.net
 */
public class ConnectionClient {

    private OutputStream out;
    private String address;
    private int port;
    private int id;

    public ConnectionClient() {
    }

    public ConnectionClient(int id, String address, int port, OutputStream out) {
        setID(id);
        setAddress(address);
        setPort(port);
        setOutputStream(out);
    }

    public final void setID(int id) {
        this.id = id;
    }

    public final void setAddress(String address) {
        this.address = address;
    }

    public final void setPort(int port) {
        this.port = port;
    }

    public final void setOutputStream(OutputStream out) {
        this.out = out;
    }

    public final int getId() {
        return this.id;
    }

    public final String getAddress() {
        return address;
    }

    public final int getPort() {
        return port;
    }

    public final OutputStream getOutputStream() {
        return out;
    }

    public final synchronized void sendPacket(Packet p) {
        try {
            this.out.write(p.getBytes());
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public final boolean matches(String address, int port) {
        if (!this.address.equals(address) || this.port != port) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.address + ":" + this.port;
    }
}
