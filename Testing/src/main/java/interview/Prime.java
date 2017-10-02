package interview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by faiter on 10/2/17.
 */
public class Prime {


    public static boolean isPrime(int nr){

        if (nr <= 1 ) return false;
        if (nr <= 3) return true;

        double lim = Math.log(nr);

        for (int i = 2; i < lim; i++) {

            if (nr % i == 0) return false;

        }

        return true;
    }

    public static void main(String[] args) {

        List<Integer> primes = new ArrayList<>();

        for (int i = 0; i < 100; i++) {

            boolean prime = isPrime(i);

            System.out.println(i+" : "+ prime);
            if (prime) primes.add(i);
        }
        System.out.println(primes);

    }

}
