/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Packets.Packet;
import java.util.LinkedList;

/**
 *
 * @author akashkrishnan@aakay.net
 */
public class ConnectionClientList<CC extends ConnectionClient> {

    private LinkedList<CC> ccl;
    private int counter;

    public ConnectionClientList() {
        ccl = new LinkedList<CC>();
    }

    public synchronized int getUniqueID() {
        return this.counter;
    }

    public synchronized int size() {
        return ccl.size();
    }

    public synchronized CC[] toArray(CC[] temp) {
        return ccl.toArray(temp);
    }

    public synchronized void add(CC cc) {
        cc.setID(counter);
        ccl.add(cc);
        counter++;
    }

    public synchronized void remove(CC cc) {
        ccl.remove(cc);
    }

    public synchronized void remove(String address, int port) {
        for (CC cc : ccl) {
            if (cc.matches(address, port)) {
                ccl.remove(cc);
                break;
            }
        }
    }

    public synchronized CC get(String address, int port) {
        for (CC cc : ccl) {
            if (cc.matches(address, port)) {
                return cc;
            }
        }
        return null;
    }

    public synchronized void broadcastToAll(Packet p) {
        for (CC cc : ccl) {
            cc.sendPacket(p);
        }
    }

    public synchronized void broadcastToOthers(Packet p, ConnectionClient client) {
        for (CC cc : ccl) {
            if (!cc.equals(client)) {
                cc.sendPacket(p);
            }
        }
    }
}
