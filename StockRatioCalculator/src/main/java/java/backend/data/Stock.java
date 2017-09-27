package backend.data;

import java.util.Arrays;

/**
 * Created by faiter on 5/25/17.
 */
public class Stock {

    private double price;
    private double percentage;

    private String name;

    public Stock(String name, double price, double percentage) {

        this.price = price;
        this.percentage = percentage;
        this.name = name;
    }

    public Stock(double price, double percentage) {

        this.price = price;
        this.percentage = percentage;
    }

    public double getPrice() {

        return price;
    }

    public double getPercentage() {

        return percentage;
    }

    public int guessAmount(){

        double ratio = percentage / price;

        System.out.println(ratio);

        int n = 1;
        while (ratio*n - (int) (ratio*n++) > 0.1);

        System.out.println(ratio*n);

        return (int) (n);
    }

    public double getRatio(){


        return percentage/price;

    }

    @Override
    public String toString() {

        return "Stock{" + "name='" + name + '\'' + '}';
    }

    public static void main(String[] args) {

       /* Stock nel = new Stock("NEL",2.13, 17.7);
        Stock xxl = new Stock("XXL", 91.50, 6.1);
        //Stock quest = new Stock("Questerre",4.45, 2.5);
*/

       Stock stock1 = new Stock("AAPL",153.87 ,39.6);
       Stock stock2 = new Stock("TSLA",316.83, 60.4);
        StockGuesser.guessAmounts(Arrays.asList(stock1, stock2/*, quest*/), AccountType.NEW_USA);
    }
}
