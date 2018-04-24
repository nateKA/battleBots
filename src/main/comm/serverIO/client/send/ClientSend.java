package main.comm.serverIO.client.send;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public abstract class ClientSend implements Runnable {
    private Socket socket;
    private PrintWriter out;
    public ClientSend(Socket s) throws IOException{
        socket = s;
        out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
    }

    public void send(String message){
        out.println(message);
    }
}
