package Client;

import java.io.IOException;
import SharedLib.*;

/**
 * Class implementation of the Client protocol, this class is used to process server messages and
 * enforce an adequate response to return to the server. Contained in the client protocol is the client implementation
 * of the wordle game.
 * Author: Ashley Travaini
 */

public class ClientProtocol extends Protocol{

    private WordleClient wordle;

    // Sets the client protocol to the start game state and returns the required client message to start the game
    public String startGame() {
        state = STARTGAME;
        wordle = new WordleClient();
        wordle.startGame();
        return STARTGAMEMESSAGE;
    }

    // Processes server messages and tracks the clients game state in order to enforce an adequate response to the server
    // Params: message - The server message to process in order to create the client response
    public String process(String message) throws UnknownMessageException {
        switch (state) {
            case STARTGAME:
                if (message.equals(GAMESTARTEDMESSAGE)) {
                    state = INPROGRESS;
                    return wordle.guessWord();
                }
                break;
            case INPROGRESS:
                // Check if the server rejected our guess
                if (message.equals(INVALIDGUESSMESSAGE))
                    return wordle.guessWord();
                // Check if the server returned a number response, if it was the game has been won
                else if (Utility.isInteger(message)) {
                    state = COMPLETE;
                    return null;
                }
                else {
                    // Check the server hint was correct before another attempt is made
                    if (wordle.validateServerHint(message))
                        return wordle.guessWord();
                }
                break;
        }
        // The server message did not follow the defined protocol, so we raise an exception
        throw new UnknownMessageException("The server has responded with an unknown message " +
                "the client will now disconnect");
    }

    // Terminates the wordle game, as WordleClient contains a BufferedReader for user input that requires closing
    public void end() throws IOException{
        wordle.endGame();
    }
}
