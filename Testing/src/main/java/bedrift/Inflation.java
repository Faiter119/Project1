package bedrift;

import java.time.LocalDate;
import java.time.Period;

/**
 * Created by faiter on 8/11/17.
 */
public class Inflation {

    private double rate;

    public Inflation(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }

    public double inYears(double amount, int years){

        System.out.println("Rate: "+rate+" - Amount: "+amount+" - Years: "+years);

        return amount * Math.pow(rate, years);

    }

    public double inYears(double originalAmount, int years, int increasePerUnit, Period interval){

        LocalDate now = LocalDate.now();
        LocalDate end = now.plusYears(years);

        System.out.println(now+" -> "+end);
        System.out.println(interval);
        System.out.println(interval.getUnits());

        int year = now.getYear();
        
        while (now.isBefore(end)){

            now = now.plus(interval);

            originalAmount += increasePerUnit;

        }

        return -1;
    }

    public Inflation combine(Inflation other){
        double origRate = other.getRate() < 1 ? 1-other.getRate() : other.getRate();
        double otherRate = this.getRate() < 1 ? 1-this.getRate() : this.getRate();
        double total = origRate > otherRate ? origRate-otherRate : otherRate-origRate;
        return new Inflation(total);
    }

    public static void main(String[] args) {

        Inflation inflation = new Inflation(0.98);
        Inflation fund = new Inflation(1.1);



        Inflation combined = inflation.combine(fund);

        int amount = 100_000_0;
        int years = 10;

        double inYears = combined.inYears(amount, 10);

        double withCompund = combined.inYears(10_000, 10, 1_000, Period.ofDays(30));

        System.out.println("");

    }
}
