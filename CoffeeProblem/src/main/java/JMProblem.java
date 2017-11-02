import org.apache.commons.math3.fraction.Fraction;

/**
 * Created by faiter on 11/2/17.
 */
public class JMProblem {

    private static Fraction ONE_FOURTH = new Fraction(1,4);

    public static void main(String[] args) {

        Cup coffee = new Cup("Coffee",new Fraction(3,4), new Fraction(0,1));
        Cup chocolate = new Cup("Chocolate", new Fraction(0,1), new Fraction(3,4));

        coffee.pour(chocolate, Fraction.ONE_THIRD); // Fill chocolate

        for (int i = 0; i < 2; i++) {

            chocolate.pour(coffee, Fraction.ONE_HALF);
            coffee.pour(chocolate, Fraction.ONE_HALF);

            System.out.println(chocolate);
            System.out.println(coffee);

        }

        chocolate.pour(coffee, ONE_FOURTH);

        System.out.println(chocolate);
        System.out.println(coffee);
    }
}
