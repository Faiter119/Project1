package interview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by faiter on 10/2/17.
 */
public class Vendingmachine {

    public enum Coin { // Comparable is automatically implemented based on ordering

        EN_KRONE(1),
        FEM_KRONER(5),
        TI_KRONER(10),
        TJUE_KRONER(20),
        FEMTI_KRONER(50); // Can easily add new coins/bills and everything works

        private int verdi;
        Coin(int verdi){this.verdi = verdi;}

        public int getVerdi() {
            return verdi;
        }

        /**
         * @return First coin smaller or equal to the value
         * Smallest possible coin or the largest coin
         */
        public static Coin smallestOfValue(int value){
            return Arrays.stream(values()).filter(coin -> coin.getVerdi() > value).findFirst().orElse(EN_KRONE).getPrev();
        }

        /**
         * @return Next ordinally or first value if at the end
         */
        public Coin getNext(){
            int index = ordinal();
            int nextIndex = index == values().length-1 ? 0 : index+1;
            return values()[nextIndex];
        }
        /**
         * @return Previous ordinally or last value if at the beginning
         */
        public Coin getPrev(){
            int index = ordinal();
            int prevIndex = index == 0 ? values().length-1 : index-1;
            return values()[prevIndex];
        }
    }

    public static List<Coin> vend(List<Coin> cost, List<Coin> coins){

        int totalCost = sum(cost);
        int totalVerdi = sum(coins);

        int toReturn = totalVerdi - totalCost;

        System.out.println("To Return: "+toReturn);

        int remaining = toReturn;

        if(totalVerdi < totalCost) return coins; // not enough money

        List<Coin> out = new ArrayList<>();

        while (sum(out) != toReturn){

            Coin smallest = Coin.smallestOfValue(remaining);

            remaining -= smallest.getVerdi();

            out.add(smallest);

            System.out.println("Added coin: "+smallest+" - "+sum(out)+" / "+(toReturn));
        }

        return out;
    }

    public static void main(String[] args) {

        List<Coin> cost = Arrays.asList(Coin.FEMTI_KRONER, Coin.TI_KRONER, Coin.EN_KRONE, Coin.EN_KRONE);
        List<Coin> pay = Arrays.asList(Coin.FEMTI_KRONER, Coin.TJUE_KRONER);

        System.out.println("Cost: "+sum(cost) + " - Pay: "+sum(pay));

        List<Coin> vend = vend(cost, pay);

        System.out.println("Compare: "+Coin.TJUE_KRONER.compareTo(Coin.FEMTI_KRONER)); // testing compareTo

        System.out.println(vend);
        System.out.println(sum(vend));

        //System.out.println(Coin.smallestOfValue(100));



        /*Coin coin = Coin.EN_KRONE;
        System.out.println(coin);

        for (int i = 0; i < 5; i++) {
            coin = coin.getNext();
            System.out.println(coin);
        }
        System.out.println();
        for (int i = 0; i < 6; i++) {
            coin = coin.getPrev();
            System.out.println(coin);
        }*/

    }

    static int sum(List<Coin> coins){
        return coins.stream().mapToInt(Coin::getVerdi).sum();
    }
}
