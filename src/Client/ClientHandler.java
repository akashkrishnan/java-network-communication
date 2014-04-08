/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

/**
 *
 * @author akashkrishnan@aakay.net
 */
public interface ClientHandler {

    public void handleConnection();

    public void handlePacket(int header, int size, byte[] data);

    public void handleDisconnection();
}
