package SharedLib;

import Server.ServerThread;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.KeyStore;

import static java.lang.Thread.sleep;

/**
 * Class implementation of utility functionality to be shared wherever needed in the game
 * Author: Ashley Travaini
 */

public class Utility {

    // Determines whether a string is an integer
    // Params: message - The message we want to check
    public static Boolean isInteger(String message) {
        try {
            int testConversion = Integer.parseInt(message);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    // Formats a given string to allow for uniform server logging messages
    // Params: message - The string to format into a logging message
    public static String serverLoggingMessage(String message) {
        return String.format("Server %s: %s", ServerThread.threadId(), message);
    }

    // Processes an exception in order to display the appropriate response to the client
    // Params: e - Exception to be checked in order to produce the correct output to the client
    public static String processClientExceptions(Exception e) {
        if (e instanceof ConnectException)
            return ("The client was unable to connect to the server, make sure the server has started and " +
                    "the port number and hostname are correct before running the client again");
        else if (e instanceof SocketException || e instanceof UnknownMessageException)
            return ("An error has occurred with the server... client is shutting down");
        else if (e instanceof UnknownHostException)
            return ("The client was unable to connect to the server. Please restart the application with an adequate host name");
        else if (e instanceof IOException)
            return ("An error has occurred in the client wordle game, try starting the game again");
        else if (e instanceof IllegalArgumentException)
            return ("The client was unable to connect to the server. Please restart the application with an adequate port number in the range of 1024-65535");
        else if (e instanceof SSLContextException)
            return "Unable to Build SSL context, ensure your certificate store is located in ./Client/ClientKeystore before restarting";
        else
            return (String.format("%s %s", e.getClass(), e.getMessage()));
    }

    // Processes an exception in order to display the appropriate response to the server
    // Params: e - Exception to be checked in order to produce the correct output to the server
    public static String processServerExceptions(Exception e) {
        if (e instanceof SocketException || e instanceof UnknownMessageException)
            return (Utility.serverLoggingMessage("An error has occurred with the client... server is shutting down"));
        else if (e instanceof IOException)
            return ("An error has occurred in the server, server is shutting down");
        else if (e instanceof SSLContextException)
            return "Unable to Build SSL context, ensure your certificate store is located in ./Server/ServerKeystore before restarting";
        else
            return (String.format("%s %s", e.getClass(), e.getMessage()));
    }

    // Holds the application for 8 seconds, this stops the windows terminal from closing immediately on application end
    public static void delayApplicationEnd() {
        try {
            sleep(8000);
        } catch (Exception e) {}
    }

    public static SSLContext createSSLContext(final String storepass, final String keystorePath) throws SSLContextException {
        try {
            // Define Keystore
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream(keystorePath), storepass.toCharArray());

            // Create key manager
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, storepass.toCharArray());
            KeyManager[] km = keyManagerFactory.getKeyManagers();

            // Create trust manager
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(keyStore);
            TrustManager[] tm = trustManagerFactory.getTrustManagers();

            // Initialize SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(km, tm, null);
            SSLContext.setDefault(sslContext);

            return sslContext;
        } catch (Exception ex) {
            throw new SSLContextException(ex.getMessage());
        }
    }
}
