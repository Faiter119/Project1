package testing;

import game.GameState;
import game.Hangman;

import java.util.Scanner;

/**
 * Created by OlavH on 17-Mar-17.
 */
public class HangmanTest {

    public static void main(String[] args) {

        Hangman game = new Hangman("Java is the best language", 10);

        Scanner scanner = new Scanner(System.in);

        while (game.getGameState() == GameState.ONGOING){

            System.out.println("Input you guess. The word is "+game.getWordLength()+" letters");
            String guess = scanner.nextLine();

            System.out.println(game.guess(guess));

            System.out.println(game.getWordSoFar());

        }

        System.out.println("Game is finished!");
        System.out.println(game.getGameState());



    }

}
