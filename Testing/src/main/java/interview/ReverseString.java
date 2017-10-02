package interview;

/**
 * Created by faiter on 10/2/17.
 */
public class ReverseString {

    /**
     * Easiest way
     */
    public static String reverseStringBuilder(String str){

        return new StringBuilder(str).reverse().toString();

    }

    public static String reverse(String str){

        String out = ""; // faster with StringBuilder, same concept

        for (int i = 0; i < str.length(); i++) {

            out = str.charAt(i)+out;

        }
        return out;
    }

    public static void main(String[] args) {

        String olavH = "OlavH";

        System.out.println(reverseStringBuilder(olavH));
        System.out.println(reverse(olavH));
    }
}
