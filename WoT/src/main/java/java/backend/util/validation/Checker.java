package backend.util.validation;

/**
 * Created by OlavH on 27-Feb-17.
 */
public class Checker {

    public static void requireNonNegative(int nr){

        if (nr < 0){
            throw new IllegalArgumentException("Negative number");
        }

    }

}
