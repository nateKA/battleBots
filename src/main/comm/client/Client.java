package main.comm.client;

import main.utils.Logger;
import main.utils.SyncObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String ip;
    private int port;
    private Logger log;
    protected SyncObject syncer = new SyncObject(true);

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        log = new Logger("Client.txt");
    }

    public Client(String ip, int port,Logger log) {
        this.ip = ip;
        this.port = port;
        this.log = log;
    }

    /**
     * Loags a string if logger is non null
     * else prints to console
     * @param str
     */
    protected void log(String str){
        if(log != null)
            log.print(str);
        else
            System.out.println(str);
    }

    /**
     * Loags a string if logger is non null
     * else prints to console
     * @param str
     */
    protected void log(String str,Object...objects){
        if(log != null)
            log.printf(str,objects);
        else
            System.out.printf(str,objects);
    }

    protected Logger getLogger(){
        return log;
    }

    /**
     * Sets the logger path
     * @param path
     */
    public void setLogger(String path){
        log = new Logger(path);
    }

    /**
     * Tells Server to use the given logger
     * @param log
     */
    public void setLogger(Logger log){
        this.log = log;
    }

    public void connect(){
        try{
			//Connect to server
			Socket socket = new Socket(ip, port);
			log.print(String.format("Client connected [%s:%d]",ip,port));

			//Start send and receive threads
			Thread th = new Thread( new Send(this,socket));
			th.start();
			Thread th2 = new Thread(new Recieve(this, socket));
			th2.start();

            th.join();
		}catch(Exception e){
            log.printError(e);
		}
    }
}
class Send extends Thread{
	public Client client;
	public Socket socket;
	private PrintWriter out;
	public Send(Client c, Socket s){
		client = c;
		socket = s;
	}
	public void run(){
        try {
            out = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
            while (true) {
                try {
                    //Scanner in = new Scanner(System.in);
                   // String message = in.nextLine();

                    long start = System.currentTimeMillis();
                    while(System.currentTimeMillis()-start < 3000);
                    String message = "q";

                    if (message.equalsIgnoreCase("q")) {
                        out.println("<DISCONNECTED>");
                        out.flush();
                        client.syncer.bool = false;
                        break;
                    }

                    client.getLogger().print("CLIENT", "SENT: " + message);
                    out.println(message);
                    out.flush();
                } catch (Exception e) {
                    client.getLogger().printError(e);
                }
            }
            client.getLogger().print("CLIENT", "Disconnected from server");
        } catch (Exception e) {
            client.getLogger().printError(e);
        }

	}
}
class Recieve extends Thread{
	Client client;
	Socket socket;
	Scanner in = null;
	public Recieve(Client c, Socket s){
		client = c;
		socket = s;
	}

	public void run(){
        try{

            in = new Scanner(new BufferedInputStream(socket.getInputStream()));
            client.getLogger().print("CLIENT","Receiver opened");
            boolean run = true;
            while(run) {
                synchronized (client.syncer){
                    if(!client.syncer.bool){
                        break;
                    }
                }

                //Read info from server
                String item = in.nextLine();
                client.getLogger().print("CLIENT", "RECIEVED: " + item);
            }
        }catch(Exception e){
            client.getLogger().printError(e);
        }
	}
}
