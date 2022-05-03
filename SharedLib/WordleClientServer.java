package SharedLib;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * The class implementation of the base Client/Server, used to define shared logic and fields that are required
 * in both the Server and Client classes.
 * Author: Ashley Travaini
 */

public abstract class WordleClientServer {

    protected SSLSocket sslSocket;
    protected PrintWriter output;
    protected BufferedReader input;
    protected Protocol protocol;
    protected SSLContext sslContext;

    // Starts the client/server by setting up it's input and output fields
    public void start() throws IOException {
        output = new PrintWriter(sslSocket.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
        sslSocket.startHandshake();
    }

    // Closes the client/server socket and it's input and output fields
    public void close() throws IOException {
        output.close();
        input.close();
        sslSocket.close();
    }

    // Processes a received message, by passing it to the protocol to retrieve the protocols' response
    // Params: input - The message to be processed
    protected String processMessage(String input) throws UnknownMessageException{
        return protocol.process(input);
    }

    // Sends a message to the other endpoint (client or server)
    // Params: message - The message to be sent
    protected void sendMessage(String message) {
        output.println(message);
    }

    // Retrieves a message from the other endpoint (client or server)
    protected String receiveMessage() throws IOException {
        String message = input.readLine();
        if (message == null)
            throw new SocketException();
        return message;
    }

    public abstract void run();
}
