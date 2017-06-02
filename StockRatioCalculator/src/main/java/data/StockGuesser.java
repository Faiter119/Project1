package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleFunction;
import java.util.stream.IntStream;

/**
 * Created by faiter on 5/25/17.
 */
public class StockGuesser {


    public static void guessAmounts(List<Stock> stocks, AccountType accountType){


        Map<Stock, Double> ratioMap = new HashMap<>();

        stocks.forEach(stock -> {

            double price = stock.getPrice();
            double percent = stock.getPercentage();

            double priceWithKurtasje = price+ (price*accountType.getKurtasje());

            double ratioWithKurtasje = percent / priceWithKurtasje;

            ratioMap.put(stock, ratioWithKurtasje);
        });


        double min = ratioMap.values().stream().mapToDouble(aDouble -> aDouble).min().getAsDouble(); // smalles ratio -> stock you have the least of

        ratioMap.entrySet().forEach(stockDoubleEntry -> {

            stockDoubleEntry.setValue(stockDoubleEntry.getValue() / min); // One of the stocks becomes 1, others a ratio of it

            System.out.println("New ratio: "+stockDoubleEntry);

        });

        ratioMap.entrySet().stream().skip(1).forEachOrdered(stockDoubleEntry -> { // skips

            DoubleFunction<Integer> function = err -> {

                int nr = 1;

                double cost = stockDoubleEntry.getValue()*nr;

                while ( cost - Math.floor(cost) > err){

                    nr++;
                    cost = stockDoubleEntry.getValue()*nr;
                }

                return nr;

            };


            //double err = Test.machineLearn(function, 15);
            //System.out.println("Err that gives 60: "+err);

            double err = 0.047875; // 0.1 -> 0.047875

            Integer nr = function.apply(err);

            System.out.println(nr);

            int finalNr = nr;

            ratioMap.forEach((stock, aDouble) -> {

                System.out.println(stock+" - "+ aDouble * finalNr);

            });


        });

    }

    public static void main(String[] args) {

        List<Integer> collected = IntStream
                .rangeClosed(1,5)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        System.out.println(collected);



    }

}
