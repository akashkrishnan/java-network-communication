package Packets;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Hashtable;

public final class PacketManager {
    
    private static final Hashtable<Integer, Class<? extends Packet>> classes = new Hashtable<Integer, Class<? extends Packet>>();
    
    /**
     * Adds new packet type to table in order to associate it with an id
     * @param c
     */
    public static void add(Class<? extends Packet> c) {
        classes.put(c.hashCode(), c);
    }
    
    /**
     * Gets name of packet with packet type associated with id
     * @param id id associated with the type of packet
     * @return name of packet
     */
    public static String getName(int id) {
        return classes.get(id).getSimpleName();
    }
    
    /**
     * Gets id associated with the type of packet
     * @param p Packet object
     * @return id of packet's type
     */
    public static int getId(Packet p) {
        return p.getClass().hashCode();
    }
    
    /**
     * Gets a new instance of packet associated with id
     * @param id
     * @return
     */
    public static Packet get(int id) {
        try {
            return classes.get(id).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Reads and interprets the data input stream and returns a packet. Type T
     * must extend Packet
     * @param in data input stream to read from
     * @return Packet of type T
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(DataInputStream in) throws IOException {
        return (T) get(in.readInt()).read(in);
    }
}
