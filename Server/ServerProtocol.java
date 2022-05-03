package Server;

import java.io.IOException;

import SharedLib.Protocol;
import SharedLib.UnknownMessageException;

/**
 * Class implementation of the servers' protocol, this class is used to process server messages and
 * enforce an adequate response to return to the client.
 * Author: Ashley Travaini
 */

public class ServerProtocol extends Protocol{

    WordleServer wordle;

    // Sets the server protocol to the start game state and returns the required server message to start the game
    public String startGame() {
        state = INPROGRESS;
        wordle = new WordleServer();
        wordle.startGame();
        return GAMESTARTEDMESSAGE;
    }

    // Processes the client message to retrieve the required response based on state and message
    // Params: message - The message to be processed
    public String process(String message) throws UnknownMessageException {
        switch (state) {
            case WAITING:
                if (message.equals(STARTGAMEMESSAGE)) {
                    state = STARTGAME;
                } else {
                    break;
                }
            case STARTGAME:
                return startGame();
            case INPROGRESS:
                message = message.toUpperCase(); // process all client messages as uppercase
                // Check that the clients guess is a valid word from the guess.txt
                if (wordle.isValidAttempt(message)) {
                    // Check if the client guess was correct
                    if (wordle.isCorrectGuess(message)) {
                        state = COMPLETE;
                        return wordle.numberOfGuesses();
                    } else
                        return wordle.produceHint(message);
                } else
                    return INVALIDGUESSMESSAGE;
            case COMPLETE:
                return ENDGAMEMESSAGE;
        }
        throw new UnknownMessageException("The client has responded with an unknown message " +
                "the client will now disconnect");
    }

    // Terminates the wordle game
    public void end() throws IOException {
        state = WAITING;
        // If the client connection drops out, garbage collection will free wordle
        if (wordle != null) {
            wordle.endGame();
        }
    }

}
