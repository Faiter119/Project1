import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
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

        //System.out.println(event);
        //System.out.println(Arrays.toString(intervals));


        BigDecimal wage = new BigDecimal(0d);
        BigDecimal payPerMinute = payPerMinute();

        TimeInterval eventInterval = TimeInterval.of(event);
        long intervalMinutes = 0; // minutes with addition

        for (TimeInterval interval : intervals){ // FIXME: 20-Apr-17 rett dag

          /*  System.out.println(interval.toStringWithDays());
            System.out.println(eventInterval);
*/
            if (interval.overlaps(eventInterval) && interval.daysMatch(eventInterval)){

                TimeInterval overlap = interval.overlapWith(eventInterval).get(); // Checked above
                //System.out.println("Overlap is "+overlap);

                // wage += time*addition

                BigDecimal addition = additionalPayMap.get(interval);
                BigDecimal thisMinuteWage = payPerMinute.add(addition.divide(BigDecimal.valueOf(60), BigDecimal.ROUND_DOWN));

                wage = wage.add(thisMinuteWage.multiply(BigDecimal.valueOf(overlap.getMinutes())));
                intervalMinutes += overlap.getMinutes();
            }
        }

        long remaining = eventInterval.getMinutes() - intervalMinutes;
        /*System.out.println(remaining+" - interval so far: "+intervalMinutes+" event: "+event+" mins: "+event.getDuration().toMinutes());
        System.out.println(breakAfter+" - "+breakDuration);
*/
        if (hasBreak(eventInterval, breakAfter)){

            remaining -= breakDuration.toMinutes();
            //System.out.println("Break! "+remaining+" - interval so far: "+intervalMinutes+" event: "+event+" mins: "+event.getDuration().toMinutes());

        }

        //if (remaining > 0) {
            wage = wage.add(payPerMinute().multiply(new BigDecimal(remaining)));
        //}

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

        Arrays.asList(days).forEach(interval::addDay);

        additionalPayMap.put(interval, extraWage);
    }

    private Duration breakAfter = Duration.ofHours(Integer.MAX_VALUE); // aldri
    private Duration breakDuration = Duration.ZERO;
    public void addUnpaidBreak(Duration afterTimeDuration, Duration durationOfBreak){

        Objects.requireNonNull(afterTimeDuration); Objects.requireNonNull(durationOfBreak);

        breakAfter = afterTimeDuration;
        breakDuration = durationOfBreak;
    }

    private boolean hasBreak(TimeInterval interval, Duration breakAfter){

        // true if the interval is lower duration than the break
        return Duration.between(interval.getStart(), interval.getEnd()).compareTo(breakAfter) == 1 ;

    }



    public static void main(String[] args) {

        Pay pay = new Pay(new BigDecimal(163.45));

        Event earlyWorkDay = new Event(LocalDate.now());
        earlyWorkDay.setStart(LocalTime.of(6,0));
        earlyWorkDay.setEnd(LocalTime.of(14,0));

        pay.addPeriodPay(LocalTime.of(6,0),LocalTime.of(7,0), new BigDecimal(45), DayOfWeek.values()); // alltid
        pay.addPeriodPay(LocalTime.of(18,0),LocalTime.of(21,0), new BigDecimal(22), DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
        pay.addPeriodPay(LocalTime.of(21,0),LocalTime.of(23,0), new BigDecimal(45), DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
        pay.addPeriodPay(LocalTime.of(13,0),LocalTime.of(16,0), new BigDecimal(45), DayOfWeek.SATURDAY);
        pay.addPeriodPay(LocalTime.of(16,0),LocalTime.of(23,0), new BigDecimal(90), DayOfWeek.SATURDAY);

        pay.addUnpaidBreak(Duration.ofMinutes(330), Duration.ofMinutes(30));


        System.out.println(pay.payFor(earlyWorkDay));

    }

}
