package interview;

/**
 * Created by faiter on 10/2/17.
 */
public class SwapNumbers {

    public static void main(String[] args) {

        int a = 10;
        int b = 20;

        a = a+b;
        b = a-b;
        a = a-b;

        System.out.println(a);
        System.out.println(b);

    }
}
