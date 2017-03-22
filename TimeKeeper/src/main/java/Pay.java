
import java.math.BigDecimal;
import java.time.*;
import java.util.*;

public class Pay {

    private BigDecimal base;
    private Currency currency;

    private Map<TimeInterval, BigDecimal> additionalPayMap = new HashMap<>();

    public Pay(BigDecimal base, Currency currency){
        this.base = base;
        this.currency = currency;
    }
    public Pay(BigDecimal base){ this(base, Currency.getInstance("NOK")); }
    public Pay(){ this(new BigDecimal(111.9)); } // Min wage in Norway

    public BigDecimal getBase(){ return base;}
    public void setBase(BigDecimal base) { this.base = base;}
    public void setCurrency(Currency currency) { this.currency = currency;}
    public Currency getCurrency(){return currency;}

    private BigDecimal payPerMinute(){
        return base.divide(new BigDecimal(60), BigDecimal.ROUND_DOWN);
    }

    public BigDecimal payFor(Event event){

        TimeInterval[] intervals = additionalPayMap.keySet().toArray(new TimeInterval[0]);
        //System.out.println(Arrays.toString(intervals));

        BigDecimal wage = new BigDecimal(0d);
        BigDecimal payPerMinute = payPerMinute();

        TimeInterval eventInterval = TimeInterval.of(event);
        long intervalMinutes = 0;

        for (TimeInterval interval : intervals){

            if (interval.overlaps(eventInterval)){

                TimeInterval overlap = interval.overlapWith(eventInterval).get(); // Checked above
                //System.out.println(overlap);

                // wage += time*addition

                BigDecimal additon = additionalPayMap.get(interval);
                BigDecimal thisMinuteWage = payPerMinute.add(additon.divide(BigDecimal.valueOf(60), BigDecimal.ROUND_DOWN));

                wage = wage.add(thisMinuteWage.multiply(BigDecimal.valueOf(overlap.getMinutes())));
                intervalMinutes += overlap.getMinutes();
            }
        }
        long remaining = eventInterval.getMinutes() - intervalMinutes;

        wage = wage.add(payPerMinute().multiply(new BigDecimal(remaining)));

        return wage;

    }
    public BigDecimal payFor(Event[] events){

        BigDecimal wage = new BigDecimal(0);

        for (Event event : events){
            wage = wage.add(payFor(event));
        }
        return wage;
    }
    public void addPeriodPay(LocalTime start, LocalTime end, BigDecimal extraWage, DayOfWeek ... days){

        TimeInterval interval = new TimeInterval(start, end);

        additionalPayMap.put(interval, extraWage);
    }



    public static void main(String[] args) {

        Pay pay = new Pay(new BigDecimal(162.45));

        Event earlyWorkDay = new Event(LocalDate.now());
        earlyWorkDay.setStart(LocalTime.of(22,0));
        earlyWorkDay.setEnd(LocalTime.of(6,0));

        pay.addPeriodPay(LocalTime.of(6,0), LocalTime.of(7,0), BigDecimal.valueOf(45), DayOfWeek.values());
        pay.addPeriodPay(LocalTime.of(19,0), LocalTime.of(23,59), BigDecimal.valueOf(90), DayOfWeek.values());

        System.out.println(pay.payFor(earlyWorkDay));

    }

}
