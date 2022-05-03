package SharedLib;

/**
 * Class implementation of the unknown message exception, thrown when the server or client receives a message that
 * does not follow the defined protocol.
 * Author: Ashley Travaini
 */

public class UnknownMessageException extends Exception {

    // Class constructor, creates an instance of the UnknownMessageException
    // Params: message - The message that the exception will contain
    public UnknownMessageException(String message) {
        super(message);
    }
}
