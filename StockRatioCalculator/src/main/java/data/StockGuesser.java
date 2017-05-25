package data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by faiter on 5/25/17.
 */
public class StockGuesser {


    public static void guessAmounts(List<Stock> stocks){


        Map<Stock, Double> ratioMap = new HashMap<>();

        stocks.forEach(stock -> {

            ratioMap.put(stock, stock.getRatio());

            //System.out.println(stock.getRatio());



        });;


        double min = ratioMap.values().stream().mapToDouble(aDouble -> aDouble).min().getAsDouble();

        ratioMap.entrySet().forEach(stockDoubleEntry -> {

            stockDoubleEntry.setValue(stockDoubleEntry.getValue() /min);

        });

        ratioMap.entrySet().forEach(System.out::println);


        ratioMap.entrySet().stream().skip(1).forEachOrdered(stockDoubleEntry -> {

            System.out.println(stockDoubleEntry);

            double err = 0.001;
            int nr = 1;

            double cost = stockDoubleEntry.getValue()*nr;

            while ( cost - Math.floor(cost) > err){
                nr++;

                //System.out.println(nr+" - "+cost+" - "+Math.floor(cost));
               /* try {
                    Thread.sleep(500);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                cost = stockDoubleEntry.getValue()*nr;

            }

            System.out.println(nr);

            int finalNr = nr;
            ratioMap.forEach((stock, aDouble) -> {

                System.out.println(stock+" - "+ aDouble * finalNr);

            });


        });

    }

}
