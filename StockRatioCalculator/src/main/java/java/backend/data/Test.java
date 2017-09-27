package backend.data;

import java.util.function.DoubleFunction;

/**
 * Created by faiter on 5/25/17.
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {

        String stock1 = "TSLA";
        String stock2 = "AAPL";

        double percentStock1 = 60;
        double percentStock2 = 40;

        double priceStock1 = 316.83;
        double priceStock2 = 153.81;

        double relStock1 = percentStock1 / priceStock1; // % per price
        double relStock2 = percentStock2 / priceStock2;

        System.out.println(stock1+": "+relStock1 + " - "+stock2+": " + relStock2);

        if (relStock1 > relStock2) {
            relStock1 = relStock1 / relStock2;
            relStock2 = 1;
        }
        else {
            relStock2 = relStock2 / relStock1;
            relStock1 = 1;

        }

        System.out.println(stock1+": "+relStock1 + " - "+stock2+": " + relStock2);

        double factor = relStock1 > relStock2 ? relStock1 : relStock2;

        System.out.println(factor);


        DoubleFunction<Integer> function = err -> {

            int nr = 1;

            while ((factor * nr) - ((int) (factor * nr)) > err) {

                nr++;
            }

            return nr;

        };

        double err = machineLearn(function, 15); // finds closest fitting value

        int multiple = function.apply(err);

        System.out.println("OPTIMAL ERR: "+err);
        System.out.println(stock1+":\t" + multiple+"\tPrice: "+priceStock1*multiple);
        System.out.println(stock2+":\t" + multiple * factor+"\tPrice: "+priceStock2*multiple);


    }

    /**
     * Finds the correct error estimate, with """machine learning""""
     */
    static double machineLearn(DoubleFunction<Integer> action, int expectedValue){

        double err = 0.01;

        while (action.apply(err) > expectedValue){

            err+=0.0000001;

        }

        //System.out.println(action.apply(err));
        return err;
    }
}
