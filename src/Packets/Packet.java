/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Packets;

import Utilities.DataConversion;
import java.util.HashMap;

/**
 *
 * @author akashkrishnan@aakay.net
 */
public abstract class Packet {

    public static final int HEADER_SIZE = 4;
    private String name;
    private byte[] header;
    private byte[] bytes;
    private byte[] sizeBytes;
    private int size;
    private HashMap<Integer, String> dataFieldNames;
    private HashMap<String, DataField<?>> dataFields;
    protected PacketManager packetManager;

    public Packet(PacketManager packetManager) {
        this.packetManager = packetManager;
        reset();
    }

    public Packet(byte[] data, PacketManager packetManager) {
        this.packetManager = packetManager;
        reset();

        byte[][] fieldData = new byte[this.dataFields.size()][];
        int i = 0;
        for (int n = 0; n < fieldData.length; n++) {
            byte[] _sizeBytes = new byte[4];
            for (int j = 0; j < _sizeBytes.length; j++) {
                _sizeBytes[j] = data[i];
                i++;
            }
            int _size = DataConversion.toInt(_sizeBytes);
            fieldData[n] = new byte[_size];
            for (int j = 0; j < _size; j++) {
                fieldData[n][j] = data[i];
                i++;
            }
            this.getDataField(n).setBytes(fieldData[n]);
        }

        updateSize();
        updateBytes();
    }

    public final Packet constructPacketFromData(byte[] data) {
        return this.packetManager.getPacketInstance(this.getHeader(), data);
    }

    public final String getName() {
        return this.packetManager.getPacketName(this.getHeader());
    }

    public final int getHeader() {
        return this.packetManager.getPacketID(this);
    }

    public final int getSize() {
        return size;
    }

    public final byte[] getHeaderBytes() {
        return header;
    }

    public final byte[] getSizeBytes() {
        return sizeBytes;
    }

    public final byte[] getBytes() {
        updateSize();
        updateBytes();
        return bytes;
    }

    private void updateSize() {
        size = 0;
        for (DataField<?> dataField : this.getDataFields()) {
            if (dataField != null) {
                size += dataField.getSize();
            }
        }
        sizeBytes = DataConversion.toByteArray(size);
        size += HEADER_SIZE + 4; //Header + Size
    }

    private void updateBytes() {
        bytes = new byte[size];
        int i = 0;

        for (byte b : header) {
            bytes[i] = b;
            i++;
        }
        for (byte b : sizeBytes) {
            bytes[i] = b;
            i++;
        }
        for (DataField<?> dataField : this.getDataFields()) {
            if (dataField != null) {
                for (byte b : dataField.getSizeBytes()) {
                    bytes[i] = b;
                    i++;
                }
                for (byte b : dataField.getBytes()) {
                    bytes[i] = b;
                    i++;
                }
            }
        }
    }

    protected abstract void initDataFields();

    public final void addDataField(String name, DataField df) {
        this.dataFieldNames.put(this.dataFieldNames.size(), name);
        this.dataFields.put(name, df);
    }

    public final DataField<?> getDataField(int index) {
        return this.dataFields.get(this.dataFieldNames.get(index));
    }

    public final DataField<?> getDataField(String name) {
        return this.dataFields.get(name);
    }

    private DataField<?>[] getDataFields() {
        DataField<?>[] fields = new DataField<?>[this.dataFields.size()];
        this.dataFields.values().toArray(fields);
        return fields;
    }

    @Override
    public String toString() {
        DataField<?>[] fields = this.getDataFields();
        String str = name + "[";
        if (fields.length != 0) {
            str += fields[0];
            for (int i = 1; i < fields.length; i++) {
                str += "," + fields[i];
            }
        }
        str += "]";
        return str;
    }

    public final void print() {
        System.out.print(this);
    }

    public final void println() {
        System.out.println(this);
    }

    public final void reset() {
        name = getName();
        header = DataConversion.toByteArray(this.getHeader());
        this.dataFieldNames = new HashMap();
        this.dataFields = new HashMap();
        this.initDataFields();
        updateSize();
        updateBytes();
    }

    public static Packet constructPacket(byte[] bytes, PacketManager pm) {
        byte[] headerBytes = new byte[Packet.HEADER_SIZE];
        byte[] sizeBytes = new byte[4];
        byte[] data = new byte[0];
        int header = 0;
        int size = 0;
        int i = 0;
        int j = 0;
        for (byte b : bytes) {
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
                    return pm.getPacketInstance(header, data);
                }
            }
        }
        return null;
    }

    public static int getHeader(byte[] bytes) {
        byte[] headerBytes = new byte[HEADER_SIZE];
        System.arraycopy(bytes, 0, headerBytes, 0, headerBytes.length);
        return DataConversion.toInt(headerBytes);
    }

    public static int getSize(byte[] bytes) {
        byte[] sizeBytes = new byte[4];
        int i = 4;
        for (int j = 0; j < sizeBytes.length; j++) {
            sizeBytes[j] = bytes[i];
            i++;
        }
        return DataConversion.toInt(sizeBytes);
    }

    public static byte[] getData(byte[] bytes) {
        return getData(bytes, getSize(bytes));
    }

    private static byte[] getData(byte[] bytes, int size) {
        byte[] data = new byte[size];
        int i = 8;
        for (int j = 0; j < data.length; j++) {
            data[j] = bytes[i];
            i++;
        }
        return data;
    }
}
