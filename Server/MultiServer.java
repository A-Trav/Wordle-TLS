package Server;

import SharedLib.Utility;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Class implementation of the whole server, this includes a pool of server threads which create and contain server endpoints
 * when needed.
 * Author: Ashley Travaini
 */

public class MultiServer {
    SSLContext sslContext;
    SSLServerSocketFactory sslSocketFactory;
    SSLServerSocket sslServerSocket;
    ExecutorService serverPool;
    private static final Logger logger = Logger.getLogger(MultiServer.class.getName());

    // Class constructor, creates an instance of the MultiServer class
    // Params: port - The port the server will bind to
    public MultiServer(Integer port) throws Exception { // throws IOException and SSLContextException
        sslContext = Utility.createSSLContext("ldEpxlfm#ef93203<d.vk", "./Server/ServerKeystore/ServerKeystore.jks");
        sslSocketFactory = sslContext.getServerSocketFactory();
        sslServerSocket = (SSLServerSocket) sslSocketFactory.createServerSocket(port);
        sslServerSocket.setNeedClientAuth(true);
        serverPool = Executors.newFixedThreadPool(20);
    }

    // Starts the MultiServer, creating the server threads as clients connect
    public void startMultiServer() {
        logger.info("Started MultiServer");
        while (true) {
            try {
                Runnable serverThread = new ServerThread((SSLSocket) sslServerSocket.accept());
                serverPool.execute(serverThread); // thread dies after the call to execute call
            } catch(IOException e) {
                logger.info("Error occurred in MultiServer" + e.getMessage());
            }
        }
    }

    // Stops the MultiServer, shutting down the server pool and the serverSocket
    public void stopMultiServer() throws IOException {
        serverPool.shutdown();
        sslServerSocket.close();
        logger.info("End MultiServer");
    }
}
