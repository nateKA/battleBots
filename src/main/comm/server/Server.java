package main.comm.server;

import main.screen.graphics.RectSprite;
import main.screen.graphics.Sprite;
import main.utils.GameUtil;
import main.utils.Logger;
import main.utils.SpriteHouse;
import main.utils.SyncObject;
import sun.rmi.runtime.Log;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;

public class Server {

    protected SyncObject syncer = new SyncObject(true);
    private String ip;
    private int port;
    public ServerSocket socket;
    protected boolean isOpen;
    protected Thread serverAccept;
    private Logger log = null;
    protected Object lock = new Object();

    private int clientNum = 0;
    private HashMap<Socket, Integer> clientList = new HashMap<>();

    public Server(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Adds a client to server list
     */
    public void addClient(Socket s){
        clientList.put(s, clientNum++);
    }

    /**
     * Returns the client's assigned number
     */
    public int getClientNum(Socket s){
        return clientList.get(s);
    }

    /**
     * Removes client from list
     */
    public void removeClient(Socket s){
        clientList.remove(s);
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

    protected Logger getLogger(){
        return log;
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

    int y = 30;
    boolean up = false;
    public String getMessage(){
        int move = up?-3:3;
        y+=move;
        //return "string:This is a public service announcement. Don't feed the Yao Guai. That is all.:100:"+y;
        String ret = SpriteHouse.blueRect.getAsString(50,y);
        ret += ";"+SpriteHouse.redRect.getAsString(30, 7+y);
        ret += ";"+SpriteHouse.redRect.getAsString(60, 15+y);
        ret += ";"+SpriteHouse.redRect.getAsString(100, y);
        ret += ";"+SpriteHouse.redRect.getAsString(200, 7+y);
        ret += ";"+SpriteHouse.redRect.getAsString(50, 10+y);
        ret += ";"+ SpriteHouse.sumoBlue.getAsString(300,y+30,0);
        ret += ";"+ SpriteHouse.sumoBlue.getAsString(20,y+10,0);
        ret += ";"+RectSprite.getAsString("orange",new Color(0,0,0).getRGB(),new Color(200,50,0).getRGB(),60,10+y,15,30);

        if(y >500 || y < 20) up = !up;
        return ret;
    }

    /**
     * Opens server socket and starts listening for clients
     * Makes blocking call while Server socket opens
     */
    public void openServer(){
        try{
			//Create socket
			 InetAddress inetAdd = InetAddress.getByName(ip);
			 socket = new ServerSocket(port, 100, inetAdd);
			 isOpen = true;
			 log.print(String.format("Socket Opened [%s:%d]",ip,port));

            //open and run ClientAccepter
            serverAccept = new ClientAccepter(this,socket);
            serverAccept.start();

            //wait for serverAccepter to start accepting clients
            synchronized (lock){
                while(((ClientAccepter)serverAccept).isRunning){
                    try{lock.wait();}catch (Exception e){}
                }
            }

            log("Now accepting clients");
		}catch(Exception e){
			log.printError(e);
			System.exit(-1);
		}


    }

    /**
     * Make Server stop accepting clients
     * Blocking call until socket is closed, or socket is found to be uncloseable
     */
    public boolean closeServer(){


        //attempt to close client accepter
        try {
            log("Requested Server to close");
            synchronized (syncer){
                syncer.bool = false;
            }

            //check is socket is running
            if(!isAccepting()){
                log("Server is not open yet");
                return false;
            }

            //close clients
            Iterator<Socket> iter = clientList.keySet().iterator();
            while(iter.hasNext()){
                iter.next().close();
            }

            //close socket
            socket.close();
            ((ClientAccepter) serverAccept).socket.close();
            serverAccept.join();
            log("Server closed");
            return true;
        }catch (Exception e){
            log("Failed to close server");
            log.printError(e);
            return false;
        }

    }

    public boolean isAccepting(){
        return ((ClientAccepter)serverAccept).isRunning;
    }

    /**
	 * @return The IP address of the user's current location
	 */
    public static String getLocalNet(){
		try {
		    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		    while (interfaces.hasMoreElements()) {
		        NetworkInterface n = interfaces.nextElement();
		        if (n.isLoopback() || !n.isUp() || n.isVirtual() || n.isPointToPoint())continue;

		        Enumeration<InetAddress> inets = n.getInetAddresses();
		        while(inets.hasMoreElements()) {
		            InetAddress curAddress = inets.nextElement();

		            final String localHostIP = curAddress.getHostAddress();
		            if(Inet4Address.class == curAddress.getClass()){
		            	//found local host
		            	return localHostIP;
		            }
		        }
		    }
		} catch (SocketException e) {
		    throw new RuntimeException(e);
		}
		return null;
	}
}
class ClientAccepter extends Thread{
    public ServerSocket socket;
    public Server server;
    public boolean isRunning = false;

	public ClientAccepter(Server s, ServerSocket socket){
		this.socket = socket;
		server = s;
	}

    public void run(){
		while(true){
			try{
			        synchronized (server.lock){
			            isRunning = true;
			            server.lock.notifyAll();
                    }
					//accepts new Clients
					Socket clientSocket = socket.accept();
			        server.addClient(clientSocket);

					//Handles client Requests
					ClientListener ch = new ClientListener(server,clientSocket);
					Thread t2 = new Thread(ch);
					t2.start();

					//sends to client
                    Sender sender = new Sender(clientSocket,server);
                    Thread t3 = new Thread(sender);
                    t3.start();

					server.getLogger().print("SERVER","Server accepted a client ["+server.getClientNum(clientSocket)+"]");

			}catch(Exception e){
				break;
				//System.exit(-1);
			}
		}
        server.getLogger().print("SERVER","Server socket stopped listening for clients");
        isRunning = false;
	}
}
class ClientListener implements Runnable {
	private Socket socket;
	private Server server;
	private int clientNum;

	public ClientListener(Server ser, Socket s){
		server = ser;
	    socket = s;
	    clientNum = server.getClientNum(s);
	}

	public void run(){
        try{
            Scanner in = new Scanner(new BufferedInputStream(socket.getInputStream()));
            String message;
            while((message = in.nextLine()) != null){
                synchronized (server.syncer){
                    if(server.syncer.bool==false){
                        break;
                    }
                }
                server.getLogger().print("SERVER",
                        String.format("RECEIVED[from:%d]: %s",clientNum,message));

                handle(message);
            }

        }catch (NoSuchElementException n){
            server.getLogger().print("SERVER","Client disconnected");
        }catch (Exception e){
            server.getLogger().printError(e);
        }
    }

    public void handle(String message){
	    if(message.equals("<DISCONNECTED>")) {
            server.removeClient(socket);
            return;
        }

    }
}
class Sender implements Runnable{
    private Socket client;
    private Server server;
    private PrintWriter out;
    public Sender(Socket c, Server s){
        client = c;
        server = s;
    }

    public void run(){
        try{
            out = new PrintWriter(new BufferedOutputStream(client.getOutputStream()));

            while(true){

                synchronized (server.syncer){
                    if(server.syncer.bool==false){
                        break;
                    }
                }

                long start = System.currentTimeMillis();

                out.println(server.getMessage());
                out.flush();

                //wait
                while(System.currentTimeMillis() - start < 1000/GameUtil.FRAME_RATE);

            }
        }catch (Exception e){
            //server.getLogger().printError(e);
        }
    }
}
