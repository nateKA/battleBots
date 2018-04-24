package main.comm.serverIO.client.receive;

import java.io.BufferedInputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class ClientReceive {

    private Socket socket;
    private Scanner in;
    public ClientReceive(Socket s){
        try {
            socket = s;
            in = new Scanner(new BufferedInputStream(s.getInputStream()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String nextLine(){
        return in.nextLine();
    }

}
