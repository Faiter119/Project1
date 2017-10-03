package interview;

/**
 * Created by faiter on 10/2/17.
 */
public class Factorial {

    public static int fact(int nr){

        if(nr <= 1) return 1;

        return nr * fact(nr-1);

    }


    public static void main(String[] args) {

        System.out.println(fact(4));




    }
}
