package Server;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

import SharedLib.*;

import javax.net.ssl.SSLSocket;

/**
 * Class implementation of the TCP Server that runs the server side of the wordle game
 * Author: Ashley Travaini
 */

public class Server extends WordleClientServer {

    private static final Logger logger = Logger.getLogger(Server.class.getName());

    // Class constructor, creates an instance of the Server class
    // Params: serverSocket - The socket we retrieve from the ServerSocket
    public Server(SSLSocket sslServerSocket) {
        sslSocket = sslServerSocket;
    }

    // Opens the server endpoint, along with all of its associated input streams and buffers
    public void start() throws IOException {
        logger.info(Utility.serverLoggingMessage("Started Server"));
        super.start();
        protocol = new ServerProtocol();
    }

    // Closes the server endpoint, along with all of its associated input streams and buffers
    public void close() throws IOException{
        protocol.end();
        super.close();
        logger.info(Utility.serverLoggingMessage("Closing Server"));
    }

    // Runs the server to wordle game, through sending and receiving messages to the client
    public void run() {
        try {
            String clientMessage, serverResponse;
            logger.info(Utility.serverLoggingMessage("Server Running"));
            // Listen to the client messages and send a response until the client wins the game
            while (true) {
                clientMessage = receiveMessage();
                serverResponse = processMessage(clientMessage);
                logger.info(Utility.serverLoggingMessage(String.format("Server received: %s. Server response: %s", clientMessage, serverResponse)));
                sendMessage(serverResponse);
                // Send final message to the client
                if (protocol.getState() == Protocol.COMPLETE) {
                    serverResponse = processMessage(null);
                    logger.info(Utility.serverLoggingMessage(String.format("Server received: %s. Server response: %s", clientMessage, serverResponse)));
                    sendMessage(serverResponse);
                    break;
                }
            }
        } catch(Exception e) {
            logger.info(Utility.processServerExceptions(e));
        }
    }
}