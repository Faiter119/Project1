package interview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by faiter on 10/2/17.
 */
public class Armstrong {

    // Armstrong number: Sum of cubed digits is equal to the number itself

    public static boolean isArmstrong(int nr){

        // Can also do this without String

        String nrAsString = String.valueOf(nr);

        int sum = nrAsString
                .chars()
                .map(i -> Integer.parseInt(String.valueOf((char) i))) // get the int
                .map(i -> (int) Math.pow(i, 3)) // cube
                .sum();

        return sum == nr;
    }

    /**
     * Version without String, harder to read IMO, but probably faster
     */
    public static boolean isArmstrongInteger(int nr){

        int sum = 0;
        int temp = nr;

        while (temp > 0){

            int rightMost = temp % 10; // Extract right digit
            temp /= 10; // remove right digit

            sum += Math.pow(rightMost, 3); // Cube
        }

        return sum == nr;
    }

    public static void main(String[] args) {

        System.out.println(isArmstrong(371)); // Know this one is an Armstrong number

        List<Integer> collected = new ArrayList<>();
        long start = System.nanoTime();
        for (int i = 100; i < 1000000; i++) {

            if (isArmstrong(i)) collected.add(i); // store the number

        }
        long end = System.nanoTime();
        long stringTime = end-start;

        System.out.println(collected);
        System.out.println("Time: "+stringTime);


        collected = new ArrayList<>();
        start = System.nanoTime();
        for (int i = 100; i < 1000000; i++) {

            if (isArmstrongInteger(i)) collected.add(i); // store the number

        }
        end = System.nanoTime();
        long intTime = end-start;

        System.out.println(collected);
        System.out.println("Time: "+intTime);

        System.out.println("Integer version faster by: "+(double)stringTime/(double)intTime);

        // Integer version is significantly faster
    }
}
