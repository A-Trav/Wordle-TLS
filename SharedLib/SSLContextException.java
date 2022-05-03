package SharedLib;

/**
 * Class implementation of the SSL context exception, thrown when the server or client are unable
 * to build their ssl context.
 * Author: Ashley Travaini
 */

public class SSLContextException extends Exception {

    // Class constructor, creates an instance of the SSLContextException
    // Params: message - The message that the exception will contain
    public SSLContextException(String message) {
        super(message);
    }
}
