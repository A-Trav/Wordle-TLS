package SharedLib;

import java.io.IOException;

/**
 * Class implementation of the wordle game, enforcing any class that inherit from it to have the base game
 * methods implemented.
 * Author: Ashley Travaini
 */

public abstract class Wordle {

    public abstract void startGame();
    public abstract void endGame() throws IOException;

}
