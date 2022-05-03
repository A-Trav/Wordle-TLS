package Server;

import SharedLib.Utility;

import java.util.logging.Logger;

/**
 * Class implementation of the server driver, this class is used to start the server application.
 * Author: Ashley Travaini
 */

public class ServerMain {

    private static final Logger logger = Logger.getLogger(ServerMain.class.getName());

    // The main method used to run the server application
    // Param: args - String array with 1 expected input, the port to bind the server to
    public static void main(String[] args) {
        try {
            System.out.println("Starting Wordle Multiserver...");
            MultiServer server = new MultiServer(Integer.parseInt(args[0]));
            server.startMultiServer();
            server.stopMultiServer();
            Utility.delayApplicationEnd();
        } catch(Exception e) {
            logger.info("Error occurred in application, try restarting the application with a port number between 1024-65535 that's not already in use");
            Utility.delayApplicationEnd();
        }
    }
}
