package main.drivers;

import main.comm.server.Server;
import main.utils.Logger;

import javax.swing.*;
import java.util.Scanner;

public class ServerDriver {

    public static void main(String[] args){

        //initialize server
        Server server = new Server(Server.getLocalNet(),8080);
        Logger log = new Logger("ServerLog.txt");
        server.setLogger(log);
        server.openServer();

        //wait for user to close server
        JDialog.setDefaultLookAndFeelDecorated(true);
        JOptionPane.showConfirmDialog(null, "Click OK to terminate server", "Server",
                JOptionPane.PLAIN_MESSAGE);

        //close server
        server.closeServer();
        log.close();
    }
}
