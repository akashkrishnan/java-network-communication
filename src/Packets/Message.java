/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Packets;

/**
 *
 * @author akashkrishnan@aakay.net
 */
public class Message extends Packet {

    public Message(byte[] data, PacketManager packetManager) {
        super(data, packetManager);
    }

    public Message(PacketManager packetManager) {
        super(packetManager);
    }

    @Override
    protected void initDataFields() {
        this.addDataField("message", new DataField<String>(""));
    }

    public String getMessage() {
        return ((DataField<String>) this.getDataField("message")).getValue();
    }

    public void setMessage(String m) {
        ((DataField<String>) this.getDataField("message")).setValue(m);
    }
}
