package Client;

import SharedLib.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;

/**
 * Class implementation of the clients wordle game, used to retrieve user input and contains client side
 * game logic and functionality.
 * Author: Ashley Travaini
 */

public class WordleClient extends Wordle {

    private String guess;
    private String hashedAnswer;
    private BufferedReader userInput;

    // Starts the wordle game for the client
    public void startGame() {
        userInput = new BufferedReader(new InputStreamReader(System.in));
    }

    // Ends the wordle game for the client
    public void endGame() throws IOException{
        userInput.close();
    }

    // Retrieves the user input for a client guess
    private String retrieveUserInput() {
        try {
            return userInput.readLine();
        } catch(IOException e) {
            System.out.println(e.getClass() + e.getMessage() + e.getCause());
            return null;
        }
    }

    // Retrieves the clients guess and checks to make sure it's an adequate attempt
    public String guessWord() {
        try {
            String input = retrieveUserInput();
            while (input == null || input.length() != 5) {
                System.out.println("Please enter a 5 letter word");
                input = retrieveUserInput();
            }
            guess = input;
        } finally {
            return guess;
        }
    }

    // Checks the server hint to determine if it's valid
    // Params: severHint - The servers hint message that we must check
    public Boolean validateServerHint(String serverHint) {
        for (int i = 0; i < guess.length(); i++) {
            if (Character.toUpperCase(guess.charAt(i)) != Character.toUpperCase(serverHint.charAt(i)) && serverHint.charAt(i) != '_')
                return false;
        }
        return true;
    }

    public Boolean EnsureServerDidNotCheat(String salt) throws NoSuchAlgorithmException{
        MessageHasher messageHasher = new MessageHasher();
        return hashedAnswer.equals(messageHasher.saltHashedMessage(messageHasher.hashMessage(guess), salt));
    }
}
