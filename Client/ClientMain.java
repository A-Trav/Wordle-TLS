package Client;

import SharedLib.Utility;

import java.net.ConnectException;

/**
 * Class implementation of the client driver, this class is used to start the client application.
 * Author: Ashley Travaini
 */

public class ClientMain {

    // The main method used to run the client application
    // Param: args - String array with 2 expected inputs, the hostname and the port to bind the client endpoint to
    public static void main(String[] args) {
        try {
            System.out.println("Connecting to the server... please wait.");
            if (args[0].length() < 6)
                throw new ConnectException();
            Client client = new Client(args[0], Integer.parseInt(args[1]));
            client.start();
            client.run();
            client.close();
            Utility.delayApplicationEnd();
        } catch(Exception e) {
            System.out.println(Utility.processClientExceptions(e));
            Utility.delayApplicationEnd();
        }
    }
}
