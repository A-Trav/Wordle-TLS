package Server;

import SharedLib.Utility;

import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Class implementation of a runnable server thread, each thread starts, runs and closes a server endpoint.
 * Author: Ashley Travaini
 */

public class ServerThread implements Runnable {

    private Server server;
    private static final Logger logger = Logger.getLogger(ServerThread.class.getName());

    // Class constructor, creates an instance of the ServerThread
    // Params: serverSocket - The server socket to pass to the server constructor
    public ServerThread(SSLSocket sslServerSocket) throws IOException {
        super();
        server = new Server(sslServerSocket);
    }

    // Runs the server endpoint
    public void run() {
        try {
            server.start();
            server.run();
            server.close();
        } catch (Exception e) {
            logger.info(Utility.serverLoggingMessage("Error occurred in ServerThread " + e.getClass()));
        }
    }

    // Retrieves the ID of the currently running server thread
    public static String threadId() {
        return Long.toString(Thread.currentThread().getId());
    }
}
