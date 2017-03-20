package game;

import java.util.regex.Pattern;

/**
 * Created by OlavH on 16-Mar-17.
 */
public class Hangman {

    private GameState gameState = GameState.ONGOING;

    private String theWordToQuess;
    private String wordSoFar;

    private int guesses = 0;
    private final int maxGuesses;

    public Hangman(String theWordToQuess, int maxGuesses) {

        this.maxGuesses = maxGuesses;
        this.theWordToQuess = theWordToQuess;

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < theWordToQuess.length(); i++) {

            builder.append("_");
        }
        wordSoFar = builder.toString();
    }

    public GameState getGameState() {return gameState;}

    public int getWordLength() {return theWordToQuess.length();}

    private boolean outOfGuesses() {

        return guesses == maxGuesses;
    }

    public boolean isFinished() {

        return win() || outOfGuesses();
    }

    public boolean win() {

        return wordSoFar.equals(theWordToQuess);
    }

    public int getMaxGuesses() {

        return maxGuesses;
    }

    public int getGuesses() {

        return guesses;
    }

    public boolean guess(String guess) {

        guesses++;

        verifyGuess(guess);


        if (guess.length() == theWordToQuess.length()) {

            return guessWholeWord(guess);
        }

        if (theWordToQuess.contains(guess)) {
            

            int count = Pattern.compile(guess).matcher(theWordToQuess).groupCount();



            wordSoFar.set(index, guess);
            updateGameState();
            return true;

        } updateGameState();
        return false;
    }

    private boolean guessWholeWord(String word) {

        if (theWordToQuess.equalsIgnoreCase(word)) {

            wordSoFar = theWordToQuess;
            updateGameState();
            return true;

        }
        updateGameState();
        return false;

    }

    public String getWordSoFar() {

        return wordSoFar;


    }

    private void updateGameState() {

        if (outOfGuesses()) {
            gameState = GameState.LOST;
        }
        if (win()) {
            gameState = GameState.WON;
        }
        else if (outOfGuesses() && !win()) {
            gameState = GameState.LOST;
        }
    }

    private void verifyGuess(String guess) {

        if (guess.length() != 1 && guess.length() != theWordToQuess.length()) {
            throw new IllegalArgumentException("Guess must be length 1 or same as the word");
        }

    }


}
