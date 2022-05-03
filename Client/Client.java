package Client;

import java.io.*;
import java.net.*;
import SharedLib.*;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * Class implementation of the TCP client that contains the logic needed to run the client side of the wordle game.
 * Author: Ashley Travaini
 */

public class Client extends WordleClientServer {

    private SSLSocketFactory sslSocketFactory;

    // Client constructor, creates an instance of the client class
    // Params: host - The hostname to bind the socket to
    // Params: port - The port number to bind the socket to
    public Client(String host, Integer port) throws Exception { // Throws SSLContextException and IOException
        sslContext = Utility.createSSLContext("dloeDFDofkdfdmdSDdmkfofkosd230323983@@#@!%#$#$%$#$", "./Client/ClientKeystore/ClientKeystore.jks");
        sslSocketFactory = sslContext.getSocketFactory();
        sslSocket = (SSLSocket) sslSocketFactory.createSocket(host, port);
        sslSocket.setEnabledCipherSuites(sslSocket.getSupportedCipherSuites());
    }

    // Starts the clients wordle game and sends a STARTGAME message to the server to let the game begin
    public void start() throws IOException {
        super.start();
        protocol = new ClientProtocol();
        sendMessage(protocol.startGame());
    }

    // Closes the client endpoint along with all buffers and streams associated with it
    public void close() throws IOException {
        protocol.end();
        super.close();
    }

    // Runs the client wordle game, through sending and receiving messages to the server
    public void run() {
        try{
            String serverMessage, clientResponse;
            // Listen for the server messages and send a response until we win the game
            while (true) {
                serverMessage = receiveMessage();
                if (protocol.getState() == Protocol.STARTGAME) {
                    System.out.println("Welcome to wordle!");
                    System.out.println(Protocol.STARTGAMEMESSAGE);
                }
                System.out.println(serverMessage);
                clientResponse = processMessage(serverMessage);
                if (protocol.getState() == Protocol.COMPLETE)
                    break;
                sendMessage(clientResponse);
            }
            // Retrieve final message from server
            if ((serverMessage = receiveMessage()) != null) {
                System.out.println(serverMessage);
                System.out.println("Thanks for playing!");
            }
        } catch(Exception e) {
            System.out.println(Utility.processClientExceptions(e));
        }
    }
}  