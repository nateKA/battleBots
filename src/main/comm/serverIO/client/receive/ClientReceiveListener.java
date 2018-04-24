package main.comm.serverIO.client.receive;

public interface ClientReceiveListener {
    /**
     * This is where implementing class decides what to do with the server message
     * @param serverMessage
     */
    void handle(String serverMessage);
}
