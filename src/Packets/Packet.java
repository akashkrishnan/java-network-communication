package Packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.TreeMap;

/**
 * All child classes MUST call addField() in its empty constructor
 */
public abstract class Packet {
    
    /**
     * Fields in packet
     */
    private final TreeMap<String, Field<?>> fields = new TreeMap<String, Field<?>>();
    
    /**
     * Reads and interprets the data input stream and returns a packet
     * @param in data input stream to read from
     * @return itself
     * @throws IOException
     */
    public final Packet read(DataInputStream in) throws IOException {
        for (Field<?> f : fields.values()) {
            f.read(in);
        }
        return this;
    }
    
    /**
     * Writes internal field data to stream
     * @param out data output stream to write to
     * @throws IOException
     */
    public final void write(DataOutputStream out) throws IOException {
        out.writeInt(getId());
        for (Field<?> f : fields.values()) {
            f.write(out);
        }
    }
    
    /**
     * @return the name of the packet
     */
    public final String getName() {
        return PacketManager.getName(getId());
    }
    
    /**
     * Returns the id associated with the packet
     * @return id of packet
     */
    public final int getId() {
        return PacketManager.getId(this);
    }
    
    /**
     * @param name name of the field
     * @param v initial value of the field
     */
    protected final <T> void addField(final String name, final T v) {
        fields.put(name, new Field<T>(v));
    }
    
    /**
     * @param name the name associated with the DataField object
     */
    @SuppressWarnings("unchecked")
    protected final <T> T getField(final String name) {
        return ((Field<T>) fields.get(name)).getValue();
    }
    
    /**
     * @param value value to set
     * @param name name of data field
     */
    @SuppressWarnings("unchecked")
    protected final <T> void setField(String name, T value) {
        ((Field<T>) fields.get(name)).setValue(value);
    }
    
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(getName()).append("[");
        for (Field<?> f : fields.values()) {
            sb.append(f).append(",");
        }
        sb.setLength(sb.length() - (fields.size() > 0 ? 1 : 0));
        return sb.append("]").toString();
    }
    
    /**
     * System.out.print(this);
     */
    public final void print() {
        System.out.print(this);
    }
    
    /**
     * System.out.println(this);
     */
    public final void println() {
        System.out.println(this);
    }
    
}
