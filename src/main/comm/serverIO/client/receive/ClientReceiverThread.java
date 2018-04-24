package main.comm.serverIO.client.receive;

import java.net.Socket;

public class ClientReceiverThread implements Runnable{

        ClientReceive client;
        ClientReceiveListener listener;
        public ClientReceiverThread(ClientReceive c,ClientReceiveListener d){
            client = c;
            listener = d;
        }

        public ClientReceiverThread(Socket s, ClientReceiveListener d){
            client = new ClientReceive(s);
            listener = d;
        }

        public void run(){
            while(true){
                String line = client.nextLine();
                listener.handle(line);
            }
        }

}
