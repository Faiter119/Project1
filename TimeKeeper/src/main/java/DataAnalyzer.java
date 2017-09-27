import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by faiter on 8/11/17.
 */
public class DataAnalyzer {

    public static void main(String[] args) throws Exception {

        String path = "./storage.txt/";

        ObjectInputStream stream = new ObjectInputStream(new FileInputStream(path));

        List<Event> events = (List<Event>) stream.readObject();

        //events.forEach(System.out::println);


        Pay pay = new Pay(new BigDecimal(166.21));
        pay.addPeriodPay(LocalTime.of(6,0),LocalTime.of(7,0), new BigDecimal(45), DayOfWeek.values()); // alltid
        pay.addPeriodPay(LocalTime.of(18,0),LocalTime.of(21,0), new BigDecimal(22), DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
        pay.addPeriodPay(LocalTime.of(21,0),LocalTime.of(23,0), new BigDecimal(45), DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
        pay.addPeriodPay(LocalTime.of(13,0),LocalTime.of(16,0), new BigDecimal(45), DayOfWeek.SATURDAY);
        pay.addPeriodPay(LocalTime.of(16,0),LocalTime.of(23,0), new BigDecimal(90), DayOfWeek.SATURDAY);

        pay.addUnpaidBreak(Duration.ofMinutes(330), Duration.ofMinutes(30));

        events.stream()
                .filter(event -> event.getDuration().toHours() < 6)
                .forEach(event -> {

                    BigDecimal payFor = pay.payFor(event);

                    System.out.println(event.getStart() + " - " + event.getEnd());
                    System.out.println("\tBetaling: "+(int)payFor.doubleValue()+"kr");

                    System.out.println("\t"+payFor.doubleValue()/event.getDuration().toHours());

                });
    }
}
