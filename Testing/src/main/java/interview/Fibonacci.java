package interview;

/**
 * Created by faiter on 10/2/17.
 */
public class Fibonacci {

    public static int fibonacci(int nr){

        if (nr <= 1) return 1;

        return fibonacci(nr-1) + fibonacci(nr-2);

    }

    public static void main(String[] args) {


        for (int i = 0; i < 100; i++) {

            System.out.println(fibonacci(i));


        }

    }
}
