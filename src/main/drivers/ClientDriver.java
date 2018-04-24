package main.drivers;

import main.comm.client.Client;
import main.utils.Logger;

public class ClientDriver {
    public static void main(String[] args){
        Logger logger = new Logger("ClientLog.txt");
        Client client = new Client("192.168.1.4",8080,logger);
        client.connect();


        logger.close();
    }
}
