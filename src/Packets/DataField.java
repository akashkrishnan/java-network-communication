/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Packets;

import Utilities.DataConversion;
import java.lang.reflect.Array;

/**
 *
 * @author akashkrishnan@aakay.net
 */
public final class DataField<T> {

    private T value;
    private byte[] bytes;
    private byte[] sizeBytes;
    private int size;

    public DataField(T value) {
        setValue(value);
    }

    public DataField(byte[] bytes) {
        setBytes(bytes);
    }

    public T getValue() {
        return value;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public byte[] getSizeBytes() {
        return sizeBytes;
    }

    public int getSize() {
        return size;
    }

    public void setValue(T value) {
        this.value = value;
        updateBytes();
        updateSize();
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
        updateValue();
        updateSize();
    }

    private void updateValue() {
        if (value instanceof Byte) {
            value = (T) (Byte) DataConversion.toByte(bytes);
        } else if (value instanceof Short) {
            value = (T) (Short) DataConversion.toShort(bytes);
        } else if (value instanceof Short[]) {
            value = (T) DataConversion.toShortArray(bytes);
        } else if (value instanceof Character) {
            value = (T) (Character) DataConversion.toChar(bytes);
        } else if (value instanceof Character[]) {
            value = (T) DataConversion.toObjectArray(DataConversion.toCharArray(bytes));
        } else if (value instanceof Integer) {
            value = (T) (Integer) DataConversion.toInt(bytes);
        } else if (value instanceof Integer[]) {
            value = (T) DataConversion.toObjectArray(DataConversion.toIntArray(bytes));
        } else if (value instanceof Long) {
            value = (T) (Long) DataConversion.toLong(bytes);
        } else if (value instanceof Long[]) {
            value = (T) DataConversion.toObjectArray(DataConversion.toLongArray(bytes));
        } else if (value instanceof Float) {
            value = (T) (Float) DataConversion.toFloat(bytes);
        } else if (value instanceof Float[]) {
            value = (T) DataConversion.toObjectArray(DataConversion.toFloatArray(bytes));
        } else if (value instanceof Double) {
            value = (T) (Double) DataConversion.toDouble(bytes);
        } else if (value instanceof Double[]) {
            value = (T) DataConversion.toObjectArray(DataConversion.toDoubleArray(bytes));
        } else if (value instanceof Boolean) {
            value = (T) (Boolean) DataConversion.toBoolean(bytes);
        } else if (value instanceof Boolean[]) {
            value = (T) DataConversion.toObjectArray(DataConversion.toBooleanArray(bytes));
        } else if (value instanceof String) {
            value = (T) DataConversion.toString(bytes);
        } else if (value instanceof String[]) {
            value = (T) DataConversion.toStringArray(bytes);
        } else if (value instanceof Packet) {
            byte[] data = Packet.getData(bytes);
            value = (T) ((Packet) value).constructPacketFromData(data);
        } else if (value instanceof Packet[]) {
            try {
                byte[] headerBytes = new byte[Packet.HEADER_SIZE];
                int i = 0;
                for (int j = 0; j < headerBytes.length; j++) {
                    headerBytes[j] = bytes[i];
                    i++;
                }
                //int header = DataConversion.toInt(headerBytes);
                byte[] lengthBytes = new byte[4];
                for (int j = 0; j < lengthBytes.length; j++) {
                    lengthBytes[j] = bytes[i];
                    i++;
                }
                int length = DataConversion.toInt(lengthBytes);
                value = (T) Array.newInstance(value.getClass().getComponentType(), length);
                for (int j = 0; j < ((Packet[]) value).length; j++) {
                    byte[] _sizeBytes = new byte[4];
                    for (int k = 0; k < _sizeBytes.length; k++) {
                        _sizeBytes[k] = bytes[i];
                        i++;
                    }
                    int _size = DataConversion.toInt(_sizeBytes);
                    byte[] data = new byte[_size];
                    for (int k = 0; k < data.length; k++) {
                        data[k] = bytes[i];
                        i++;
                    }
                    ((Packet[]) value)[j] = ((Packet) value).constructPacketFromData(data);
                }
            } catch (Exception ex) {
                System.out.println();
            }
        } else {
            value = (T) DataConversion.toObject(bytes);
        }
    }

    private void updateBytes() {
        if (value instanceof Byte) {
            bytes = DataConversion.toByteArray((Byte) value);
        } else if (value instanceof Short) {
            bytes = DataConversion.toByteArray((Short) value);
        } else if (value instanceof Short[]) {
            bytes = DataConversion.toByteArray((Short[]) value);
        } else if (value instanceof Character) {
            bytes = DataConversion.toByteArray((Character) value);
        } else if (value instanceof Character[]) {
            bytes = DataConversion.toByteArray((Character[]) value);
        } else if (value instanceof Integer) {
            bytes = DataConversion.toByteArray((Integer) value);
        } else if (value instanceof Integer[]) {
            bytes = DataConversion.toByteArray((Integer[]) value);
        } else if (value instanceof Long) {
            bytes = DataConversion.toByteArray((Long) value);
        } else if (value instanceof Long[]) {
            bytes = DataConversion.toByteArray((Long[]) value);
        } else if (value instanceof Float) {
            bytes = DataConversion.toByteArray((Float) value);
        } else if (value instanceof Float[]) {
            bytes = DataConversion.toByteArray((Float[]) value);
        } else if (value instanceof Double) {
            bytes = DataConversion.toByteArray((Double) value);
        } else if (value instanceof Double[]) {
            bytes = DataConversion.toByteArray((Double[]) value);
        } else if (value instanceof Boolean) {
            bytes = DataConversion.toByteArray((Boolean) value);
        } else if (value instanceof Boolean[]) {
            bytes = DataConversion.toByteArray((Boolean[]) value);
        } else if (value instanceof String) {
            bytes = DataConversion.toByteArray((String) value);
        } else if (value instanceof String[]) {
            bytes = DataConversion.toByteArray((String[]) value);
        } else if (value instanceof Packet) {
            bytes = ((Packet) value).getBytes();
        } else if (value instanceof Packet[]) {
            try {
                Packet temp = (Packet) ((Packet[]) value).getClass().getComponentType().newInstance();
                int totalSize = 4; //Size of bytes needed to express header
                totalSize += 4; //Size of bytes needed to express length of array
                for (Packet p : (Packet[]) value) {
                    totalSize -= 4; //Size of bytes removed that expresses size of packet
                    totalSize += p.getBytes().length; //Size of bytes needed to express packet
                }
                bytes = new byte[totalSize];
                int i = 0;
                byte[] headerBytes = temp.getHeaderBytes();
                byte[] lengthBytes = DataConversion.toByteArray(((Packet[]) value).length);

                for (int j = 0; j < headerBytes.length; j++) {
                    bytes[i] = headerBytes[j];
                    i++;
                }
                for (int j = 0; j < lengthBytes.length; j++) {
                    bytes[i] = lengthBytes[j];
                    i++;
                }
                for (Packet p : (Packet[]) value) {
                    byte[] data = new byte[p.getBytes().length - 4];
                    for (int j = 4; j < p.getBytes().length; j++) {
                        bytes[i] = p.getBytes()[j];
                        i++;
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else {
            bytes = DataConversion.toByteArray(value);
        }
    }

    private void updateSize() {
        sizeBytes = DataConversion.toByteArray(bytes.length);
        size = 4 + bytes.length;
    }

    @Override
    public String toString() {
        if (value instanceof Object[]) {
            String str = "{";
            if (((Object[]) value).length != 0 && ((Object[]) value)[0] != null) {
                str += ((Object[]) value)[0].toString();
                for (int i = 1; i < ((Object[]) value).length; i++) {
                    str += "," + ((Object[]) value)[i];
                }
            }
            str += "}";
            return str;
        }
        return value.toString();
    }
}
