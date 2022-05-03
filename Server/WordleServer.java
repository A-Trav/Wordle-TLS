package Server;

import SharedLib.MessageHasher;
import SharedLib.Utility;
import SharedLib.Wordle;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import java.util.logging.*;

/**
 * Class implementation of the servers wordle game, used to obtain a random word from the target.txt file for the user
 * to guess. Also reads the words from the guess.txt file into a string list to check that client guess' are adequate.
 * Contains all the Servers wordle game logic and functionality.
 * Author: Ashley Travaini
 */

public class WordleServer extends Wordle {

    private String word;
    private String salt;
    private List<String> guessWordList;
    private int numberOfGuesses;
    private static final Logger logger = Logger.getLogger(WordleServer.class.getName());

    // Starts the servers wordle game
    public void startGame() {
        word = newTargetWord();
        constructGuessWordList();
        numberOfGuesses = 0;
        logger.info(Utility.serverLoggingMessage("Server started Wordle game, target word is: " + word));
    }

    // Ends the servers wordle game
    public void endGame() throws IOException{
        logger.info(Utility.serverLoggingMessage("Server finished Wordle game, number of client guesses: " + numberOfGuesses()));
        word = "";
        guessWordList.clear();
        numberOfGuesses = 0;
    }

    // Retrieves a new random word from the target.txt file
    private String newTargetWord() {
        try {
            List<String> wordList = Files.readAllLines(new File("./text/target.txt").toPath(), Charset.defaultCharset());
            return wordList.get(new Random().nextInt(wordList.size()));
        } catch(IOException e) {
            logger.info(Utility.serverLoggingMessage("Server was unable to read target word from target.txt. " + e.getClass()));
            return null;
        }
    }

    // Builds the WordleServer's string list, containing words from the guess.txt file
    private void constructGuessWordList() {
        try {
            guessWordList = Files.readAllLines(new File("./text/guess.txt").toPath(), Charset.defaultCharset());
        } catch(IOException e) {
            logger.info(Utility.serverLoggingMessage("Server was unable to read guess word list from guess.txt " + e.getClass()));
        }
    }

    // Creates a hint based on correct characters in the string parameter, in comparison to the stored target word
    // Params: clientMessage - The message to create the hint from
    public String produceHint(String clientMessage) {
        char[] charsForUse = word.toCharArray();
        char[] hint = "_____".toCharArray();

        for (int i = 0; i < word.length(); i++) {
            if (clientMessage.charAt(i) == word.charAt(i)) {
                hint[i] = Character.toUpperCase(clientMessage.charAt(i));
                charsForUse[i] = '_';
            }
            else {
                for (int j = 0; j < word.length(); j++) {
                    if (clientMessage.charAt(i) == word.charAt(j) && clientMessage.charAt(j) != word.charAt(j) && charsForUse[j] != '_') {
                        hint[i] = Character.toLowerCase(clientMessage.charAt(i));
                        charsForUse[j] = '_';
                    }
                }
            }
        }
        return new String(hint);
    }

    // Checks that the parameter string can be found in the valid guess list
    // Params: message - The message to check
    public Boolean isValidAttempt(String message) {
        Boolean validGuess = guessWordList.contains(message);
        if (validGuess)
            numberOfGuesses++;
        return validGuess;
    }

    // Checks whether the parameter string is the target word
    // Params: message - The message to check
    public Boolean isCorrectGuess(String message) {
        return message.equals(word);
    }

    // Returns the tracked number of guesses of the client
    public String numberOfGuesses() {
        return Integer.toString(numberOfGuesses);
    }

    public String hashAndSaltAnswer() throws NoSuchAlgorithmException {
        MessageHasher messageHasher = new MessageHasher();
        salt = messageHasher.generateSalt(256);
        return messageHasher.saltHashedMessage(messageHasher.hashMessage(word), salt);
    }

}
