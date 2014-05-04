package Packets;

public class Message extends Packet {
    
    /**
     * Makes new Message packet with message field of type string
     */
    public Message() {
        addField("message", "");
    }
    
    /**
     * Gets the message field string
     * @return message string
     */
    public String getMessage() {
        return getField("message");
    }
    
    /**
     * Sets the message field string and returns itself
     * @param m message string
     */
    public void setMessage(String m) {
        setField("message", m);
    }
    
}
