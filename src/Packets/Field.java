package Packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;

public final class Field<T> {
    
    /**
     * Value of field
     *?
    private T v;
    
    /**
     * Constructs a new Field object with a value
     * @param value Value of Field object
     */
    public Field(T v) {
        setValue(v);
    }
    
    /**
     * Reads stream to get data of field
     * @param in data input stream to read from
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public void read(DataInputStream in) throws IOException {
        if (v instanceof Boolean) {
            v = (T) (Boolean) in.readBoolean();
        } else if (v instanceof Boolean[]) {
            Boolean[] _v = (Boolean[]) Array.newInstance(((Boolean[]) v).getClass().getComponentType(), in.readInt());
            for (int i = 0; i < _v.length; i++) {
                _v[i] = in.readBoolean();
            }
            v = (T) _v;
        } else if (v instanceof Character) {
            v = (T) (Character) in.readChar();
        } else if (v instanceof Character[]) {
            Character[] _v = (Character[]) Array.newInstance(((Character[]) v).getClass().getComponentType(),
                    in.readInt());
            for (int i = 0; i < _v.length; i++) {
                _v[i] = in.readChar();
            }
            v = (T) _v;
        } else if (v instanceof Byte) {
            v = (T) (Byte) in.readByte();
        } else if (v instanceof Byte[]) {
            Byte[] _v = (Byte[]) Array.newInstance(((Byte[]) v).getClass().getComponentType(), in.readInt());
            for (int i = 0; i < _v.length; i++) {
                _v[i] = in.readByte();
            }
            v = (T) _v;
        } else if (v instanceof Short) {
            v = (T) (Short) in.readShort();
        } else if (v instanceof Short[]) {
            Short[] _v = (Short[]) Array.newInstance(((Short[]) v).getClass().getComponentType(), in.readInt());
            for (int i = 0; i < _v.length; i++) {
                _v[i] = in.readShort();
            }
            v = (T) _v;
        } else if (v instanceof Integer) {
            v = (T) (Integer) in.readInt();
        } else if (v instanceof Integer[]) {
            Integer[] _v = (Integer[]) Array.newInstance(((Integer[]) v).getClass().getComponentType(), in.readInt());
            for (int i = 0; i < _v.length; i++) {
                _v[i] = in.readInt();
            }
            v = (T) _v;
        } else if (v instanceof Long) {
            v = (T) (Long) in.readLong();
        } else if (v instanceof Long[]) {
            Long[] _v = (Long[]) Array.newInstance(((Long[]) v).getClass().getComponentType(), in.readInt());
            for (int i = 0; i < _v.length; i++) {
                _v[i] = in.readLong();
            }
            v = (T) _v;
        } else if (v instanceof Float) {
            v = (T) (Float) in.readFloat();
        } else if (v instanceof Float[]) {
            Float[] _v = (Float[]) Array.newInstance(((Float[]) v).getClass().getComponentType(), in.readInt());
            for (int i = 0; i < _v.length; i++) {
                _v[i] = in.readFloat();
            }
            v = (T) _v;
        } else if (v instanceof Double) {
            v = (T) (Double) in.readDouble();
        } else if (v instanceof Double[]) {
            Double[] _v = (Double[]) Array.newInstance(((Double[]) v).getClass().getComponentType(), in.readInt());
            for (int i = 0; i < _v.length; i++) {
                _v[i] = in.readDouble();
            }
            v = (T) _v;
        } else if (v instanceof String) {
            v = (T) in.readUTF();
        } else if (v instanceof String[]) {
            String[] _v = (String[]) Array.newInstance(((String[]) v).getClass().getComponentType(), in.readInt());
            for (int i = 0; i < _v.length; i++) {
                _v[i] = in.readUTF();
            }
            v = (T) _v;
        } else if (v instanceof Packet) {
            v = PacketManager.get(in);
        } else if (v instanceof Packet[]) {
            Packet[] _v = (Packet[]) Array.newInstance(((Packet[]) v).getClass().getComponentType(), in.readInt());
            for (int i = 0; i < _v.length; i++) {
                _v[i] = PacketManager.get(in);
            }
            v = (T) _v;
        } else if (v instanceof Serializable) {
            try (ObjectInputStream _in = new ObjectInputStream(in)) {
                v = (T) _in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (v instanceof Serializable[]) {
            Object[] _v = (Object[]) Array.newInstance(((Object[]) v).getClass().getComponentType(), in.readInt());
            try (ObjectInputStream _in = new ObjectInputStream(in)) {
                for (int i = 0; i < _v.length; i++) {
                    _v[i] = _in.readObject();
                }
                v = (T) _v;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Writes data of field to stream
     * @param out data output stream to write to
     * @throws IOException
     */
    public void write(DataOutputStream out) throws IOException {
        if (v instanceof Boolean) {
            out.writeBoolean((Boolean) v);
        } else if (v instanceof Boolean[]) {
            Boolean[] _v = (Boolean[]) v;
            out.writeInt(_v.length);
            for (Boolean e : _v) {
                out.writeBoolean(e);
            }
        } else if (v instanceof Character) {
            out.writeChar((Character) v);
        } else if (v instanceof Character[]) {
            Character[] _v = (Character[]) v;
            out.writeInt(_v.length);
            for (Character e : _v) {
                out.writeChar(e);
            }
        } else if (v instanceof Byte) {
            out.writeByte((Byte) v);
        } else if (v instanceof Byte[]) {
            Byte[] _v = (Byte[]) v;
            out.writeInt(_v.length);
            for (Byte e : _v) {
                out.writeByte(e);
            }
        } else if (v instanceof Short) {
            out.writeShort((Short) v);
        } else if (v instanceof Short[]) {
            Short[] _v = (Short[]) v;
            out.writeInt(_v.length);
            for (Short e : _v) {
                out.writeShort(e);
            }
        } else if (v instanceof Integer) {
            out.writeInt((Integer) v);
        } else if (v instanceof Integer[]) {
            Integer[] _v = (Integer[]) v;
            out.writeInt(_v.length);
            for (Integer e : _v) {
                out.writeInt(e);
            }
        } else if (v instanceof Long) {
            out.writeLong((Long) v);
        } else if (v instanceof Long[]) {
            Long[] _v = (Long[]) v;
            out.writeInt(_v.length);
            for (Long e : _v) {
                out.writeLong(e);
            }
        } else if (v instanceof Float) {
            out.writeFloat((Float) v);
        } else if (v instanceof Float[]) {
            Float[] _v = (Float[]) v;
            out.writeInt(_v.length);
            for (Float e : _v) {
                out.writeFloat(e);
            }
        } else if (v instanceof Double) {
            out.writeDouble((Double) v);
        } else if (v instanceof Double[]) {
            Double[] _v = (Double[]) v;
            out.writeInt(_v.length);
            for (Double e : _v) {
                out.writeDouble(e);
            }
        } else if (v instanceof String) {
            out.writeUTF((String) v);
        } else if (v instanceof String[]) {
            String[] _v = (String[]) v;
            out.writeInt(_v.length);
            for (String e : _v) {
                out.writeUTF(e);
            }
        } else if (v instanceof Packet) {
            ((Packet) v).write(out);
        } else if (v instanceof Packet[]) {
            Packet[] _v = (Packet[]) v;
            out.writeInt(_v.length);
            for (Packet e : _v) {
                e.write(out);
            }
        } else if (v instanceof Serializable) {
            try (ObjectOutputStream _out = new ObjectOutputStream(out)) {
                _out.writeObject(v);
            }
        } else if (v instanceof Serializable[]) {
            try (ObjectOutputStream _out = new ObjectOutputStream(out)) {
                Object[] _v = (Object[]) Array.newInstance(((Serializable[]) v).getClass().getComponentType(),
                        ((Serializable[]) v).length);
                for (Object e : _v) {
                    _out.writeObject(e);
                }
            }
        }
    }
    
    /**
     * Sets DataField object's value
     * @param value Value of a DataField object
     */
    public void setValue(final T v) {
        this.v = v;
    }
    
    /**
     * @return Field object's value
     */
    public T getValue() {
        return v;
    }
    
    @Override
    public String toString() {
        if (v instanceof Object[]) {
            final Object[] objs = (Object[]) v;
            final StringBuffer sb = new StringBuffer("{");
            for (final Object o : objs) {
                sb.append(o).append(",");
            }
            sb.setLength(sb.length() - (objs.length > 0 ? 1 : 0));
            return sb.append("}").toString();
        }
        return v.toString();
    }
    
}
