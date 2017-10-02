package interview;

/**
 * Created by faiter on 10/2/17.
 */
public class FizzBuzz {

    public static String fizzBuzz(int nr){

        String out = "";

        if (nr % 3 == 0) out += "Fizz";
        if (nr % 5 == 0) out += "Buzz";

        return out.isEmpty() ? String.valueOf(nr) : out;
    }

    public static void main(String[] args) {

        for (int i = 1; i < 100; i++) {

            System.out.println(fizzBuzz(i));

        }

    }

}
