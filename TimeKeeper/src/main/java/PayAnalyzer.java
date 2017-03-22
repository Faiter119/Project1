import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Olav Husby on 16.03.2017.
 */
public class PayAnalyzer {


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String path = "./storage.txt/";

        ObjectInputStream stream = new ObjectInputStream(new FileInputStream(path));

        List<Event> events = (List<Event>) stream.readObject();


        int sum = events.stream()
                .mapToInt(event -> (int) event.getDuration().toHours())
                .sum();

        System.out.println(sum);

        int sum1 = events.stream()
                .filter(event -> event.getEnd().isAfter(LocalTime.of(16, 0)))
                .filter(event -> event.getDate().getDayOfWeek() == DayOfWeek.SATURDAY)
                .mapToInt(event -> (int) event.getDuration().toHours())
                .sum(); // sluttet etter 14, altså tillegg

        System.out.println(sum1);


        Pay pay = new Pay(new BigDecimal(163.45));
        pay.addPeriodPay(LocalTime.of(6,0),LocalTime.of(7,0), new BigDecimal(45), DayOfWeek.values()); // alltid
        pay.addPeriodPay(LocalTime.of(18,0),LocalTime.of(21,0), new BigDecimal(22), DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
        pay.addPeriodPay(LocalTime.of(21,0),LocalTime.of(23,0), new BigDecimal(45), DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
        pay.addPeriodPay(LocalTime.of(13,0),LocalTime.of(16,0), new BigDecimal(45), DayOfWeek.SATURDAY);
        pay.addPeriodPay(LocalTime.of(16,0),LocalTime.of(23,0), new BigDecimal(90), DayOfWeek.SATURDAY);



        LocalDate dayBeforeStart = LocalDate.of(2016,5,26);
        LocalDate dayAfterEnd = LocalDate.of(2016,8,14);

        double sumForSummer = events.stream()
                .filter(event -> event.getDate().isAfter(dayBeforeStart) && event.getDate().isBefore(dayAfterEnd))
                .mapToDouble(value -> pay.payFor(value).doubleValue())
                .sum();

        System.out.println(sumForSummer);



        double sumTotal = events.stream()
                //.filter(event -> event.getDate().isAfter(LocalDate.of(2017,1,31)) && event.getDate().isBefore(LocalDate.of(2017,3,1)))
                .mapToDouble(value -> pay.payFor(value).doubleValue())
                .sum();

        System.out.println(sumTotal);

        double start6 = events.stream()
                .filter(event -> event.getStart().equals(LocalTime.of(6, 0)))
                .filter(event -> event.getEnd().equals(LocalTime.of(14, 0)))
                .mapToDouble(value -> pay.payFor(value).doubleValue())
                .sum();

        System.out.println(start6);
        //events.stream().map(event -> TimeInterval.of(event))

        System.out.println(getEventsByMonth(events));

        System.out.println(getSumByMonth(events, pay));


    }

    static Map<Month, Integer> getEventsByMonth(List<Event> events){

        Map<Month, Integer> map = new HashMap<>();

        events.forEach(event -> {

            if (map.containsKey(event.getDate().getMonth())){

                map.put(event.getDate().getMonth(),map.get(event.getDate().getMonth())+1);
            }
            else map.put(event.getDate().getMonth(), 1);

        });

        return map;
    }

    static Map<Month, Integer> getSumByMonth(List<Event> events, Pay pay){

        Map<Month, Integer> map = new HashMap<>();



        events.forEach(event -> {

            if (map.containsKey(event.getDate().getMonth())){

                map.put(event.getDate().getMonth(), map.get(event.getDate().getMonth())+pay.payFor(event).intValue());
            }
            else map.put(event.getDate().getMonth(), pay.payFor(event).intValue());

        });
        return map;
    }


}
