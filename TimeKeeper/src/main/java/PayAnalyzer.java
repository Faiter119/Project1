import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Olav Husby on 16.03.2017.
 */
public class PayAnalyzer {


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String path = "./storage.txt/";

        ObjectInputStream stream = new ObjectInputStream(new FileInputStream(path));

        List<Event> events = (List<Event>) stream.readObject();


        int totalHoursAllTime = events.stream()
                .mapToInt(event -> (int) event.getDuration().toHours())
                .sum();

        System.out.println("Total hours all time: "+totalHoursAllTime);

        /*int sum1 = events.stream()
                .filter(event -> event.getEnd().isAfter(LocalTime.of(16, 0)))
                .filter(event -> event.getDate().getDayOfWeek() == DayOfWeek.SATURDAY)
                .mapToInt(event -> (int) event.getDuration().toHours())
                .sum(); // sluttet etter 14, altså tillegg

        System.out.println(sum1);*/


        Pay pay = new Pay(new BigDecimal(166.21));
        pay.addPeriodPay(LocalTime.of(6,0),LocalTime.of(7,0), new BigDecimal(45), DayOfWeek.values()); // alltid
        pay.addPeriodPay(LocalTime.of(18,0),LocalTime.of(21,0), new BigDecimal(22), DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
        pay.addPeriodPay(LocalTime.of(21,0),LocalTime.of(23,0), new BigDecimal(45), DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
        pay.addPeriodPay(LocalTime.of(13,0),LocalTime.of(16,0), new BigDecimal(45), DayOfWeek.SATURDAY);
        pay.addPeriodPay(LocalTime.of(16,0),LocalTime.of(23,0), new BigDecimal(90), DayOfWeek.SATURDAY);

        pay.addUnpaidBreak(Duration.ofMinutes(330), Duration.ofMinutes(30));


        /*LocalDate dayBeforeStart = LocalDate.of(2016,5,26);
        LocalDate dayAfterEnd = LocalDate.of(2016,8,14);

        double sumForSummer = events.stream()
                .filter(event -> event.getDate().isAfter(dayBeforeStart) && event.getDate().isBefore(dayAfterEnd))
                .mapToDouble(value -> pay.payFor(value).doubleValue())
                .sum();

        System.out.println(sumForSummer);*/



        double totalPayAllTime = events.stream()
                //.filter(event -> event.getDate().isAfter(LocalDate.of(2017,1,31)) && event.getDate().isBefore(LocalDate.of(2017,3,1)))
                .mapToDouble(value -> pay.payFor(value).doubleValue())
                .sum();

        System.out.println("Total pay all time: "+totalPayAllTime);

        double totalPayStart6 = events.stream()
                .filter(event -> event.getStart().equals(LocalTime.of(6, 0)))
                //.filter(event -> event.getEnd().equals(LocalTime.of(14, 0)))
                .mapToDouble(value -> pay.payFor(value).doubleValue())
                .sum();

        System.out.println("Total pay start 6: "+totalPayStart6);
        //events.stream().map(event -> TimeInterval.of(event))

        Map<Month, Integer> eventsByMonth = getEventsByMonth(events);
        Map<Month, Long> totalHoursByMoneth = getTotalHoursByMoneth(events);
        Map<Month, Integer> sumByMonth = getSumByMonth(events, pay);


        List<Month> months = Arrays.asList(Month.values());

        months.forEach(month -> {

            System.out.println("******************");
            System.out.println(month);
            System.out.println("Events: \t"+eventsByMonth.get(month));
            if (totalHoursByMoneth.get(month) != null)System.out.println("Hours: \t\t"+totalHoursByMoneth.get(month)+" (avg: "+(double)totalHoursByMoneth.get(month)/(double)eventsByMonth.get(month)+" )");
            System.out.println("Sum: \t\t"+sumByMonth.get(month)+"kr");

        });

        System.out.println();

        String HLP = "HLP".toLowerCase();
        String SS = "SS".toLowerCase();

        int hlpTimer =
                events.stream()
                .filter(event -> event.getDescription().toLowerCase().contains(HLP))
                .mapToInt(value -> (int) value.getDuration().toHours())
                .sum();

        int ssTimer =
                events.stream()
                        .filter(event -> event.getDescription().toLowerCase().contains(SS))
                        .mapToInt(value -> (int) value.getDuration().toHours())
                        .sum();

        System.out.println("HLP: "+hlpTimer+" - SS: "+ssTimer+" - prosent SS: "+new DecimalFormat("#.00").format((100d/totalHoursAllTime) * ssTimer)+"%");
        System.out.println("Totalt: "+totalHoursAllTime +" - HLP+SS: "+(hlpTimer+ssTimer));
        System.out.println("Both departments: "+Math.abs(totalHoursAllTime-(hlpTimer+ssTimer)));


        List<Event> eventsWithNoDepartment = events.stream()
                .filter(event -> !event.getDescription().toLowerCase().contains(HLP) && !event.getDescription().toLowerCase().contains(SS))
                .collect(Collectors.toList());

        System.out.println();

        System.out.println("EFFEKTIV TIMELØNN: "+totalPayAllTime/totalHoursAllTime);
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

    static Map<Month, Long> getTotalHoursByMoneth(List<Event> events){

        Map<Month, Long> map = new HashMap<>();

        events.forEach(event -> {

            if (map.containsKey(event.getDate().getMonth())){

                map.put(event.getDate().getMonth(),map.get(event.getDate().getMonth())+event.getDuration().toHours());
            }
            else map.put(event.getDate().getMonth(), event.getDuration().toHours());

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
