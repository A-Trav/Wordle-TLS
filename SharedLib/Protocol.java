package SharedLib;

import java.io.IOException;

/**
 * Class implementation of the base Protocol class, this class contains the base functionality of the protocol
 * including it's valid states and messages that can be sent (excluding user input). This class also enforces
 * other protocols that inherit from it to have the required functionality implemented.
 * Author: Ashley Travaini
 */

public abstract class Protocol {
    public static final int WAITING = 0;
    public static final int STARTGAME = 1;
    public static final int INPROGRESS = 2;
    public static final int COMPLETE = 3;
    public static final String STARTGAMEMESSAGE = "START GAME";
    public static final String ENDGAMEMESSAGE = "GAME OVER";
    public static final String GAMESTARTEDMESSAGE = "_____";
    public static final String INVALIDGUESSMESSAGE = "INVALID GUESS";

    protected int state = WAITING;

    public abstract String process(String message) throws UnknownMessageException;

    public abstract String startGame();

    public abstract void end() throws IOException;

    public int getState() {
        return state;
    }
}
