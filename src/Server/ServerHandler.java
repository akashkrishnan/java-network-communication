/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author akashkrishnan@aakay.net
 */
public interface ServerHandler<CC extends ConnectionClient> {

    public void handleConnection(CC cc);

    public void handlePacket(int header, int size, byte[] data, CC cc);

    public void handleDisconnection(CC cc);
}
