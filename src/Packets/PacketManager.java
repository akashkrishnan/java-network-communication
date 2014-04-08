/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Packets;

import Utilities.Security;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 *
 * @author akashkrishnan@aakay.net
 */
public final class PacketManager {

    private int count;
    private HashMap<Integer, Class> packetTable;
    private HashMap<Class, Integer> pidTable;

    public PacketManager() {
        this.packetTable = new HashMap<Integer, Class>();
        this.pidTable = new HashMap<Class, Integer>();
    }

    public void add(Class c) {
        if (!this.packetTable.containsValue(c)) {
            this.packetTable.put(this.count, c);
            this.pidTable.put(c, this.count);
            this.count++;
        }
    }

    public int getPacketID(Packet p) {
        return this.pidTable.get(p.getClass());
    }

    public Class getPacketClass(int pid) {
        return this.packetTable.get(pid);
    }

    public String getPacketName(int pid) {
        return this.packetTable.get(pid).getSimpleName();
    }

    public Packet getPacketInstance(int pid) {
        try {
            Constructor c = this.packetTable.get(pid).getConstructor(PacketManager.class);
            return (Packet) c.newInstance(this);
        } catch (Exception ex) {
            System.out.println("[ERROR] Unable to getPacketInstance("+pid+"): " + ex);
        }
        return null;
    }

    public Packet getPacketInstance(int pid, byte[] data) {
        try {
            Constructor c = this.packetTable.get(pid).getConstructor(Class.forName("[B"), PacketManager.class);
            return (Packet) c.newInstance(data, this);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    public String getHash() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
            bos.close();
            return Security.encrypt(Security.toHexString(bos.toByteArray()));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }
}
