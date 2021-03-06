import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;

public class TimeInterval implements Serializable{

    private LocalTime start;
    private LocalTime end;
    private DayOfWeek[] days = new DayOfWeek[0];

    public TimeInterval(LocalTime start, LocalTime end){

        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() { return start; }
    public LocalTime getEnd() { return end; }
    public void setDays(DayOfWeek ... days){ this.days = days; }
    public long getMinutes(){

        int minutesPerDay = 60*24;

        long minutes = ChronoUnit.MINUTES.between(start, end);

        return minutes < 0 ? minutesPerDay-Math.abs(minutes) : minutes;
        //return ChronoUnit.MINUTES.between(start, end);
    }
    public Optional<DayOfWeek[]> getDays(){

        if(days.length == 0) return Optional.empty();
        else return Optional.of(days);
    }
    public void addDay(DayOfWeek day){
        DayOfWeek[] newDays = Arrays.copyOf(days, days.length+1);
        newDays[newDays.length-1] = day;
        days = newDays;
    }

    // FIXME: 28.10.2016 Does not work when over midnight
    public boolean overlaps(TimeInterval interval){

        LocalTime iStart = interval.getStart();
        LocalTime iEnd = interval.getEnd();

        return !start.isAfter(iEnd) && !iStart.isAfter(end); // For not overlapWith at same min: return start.isBefore(iEnd) && iStart.isBefore(end);

        /*if( start.equals(iStart) || end.equals(iEnd) ) return true;

        if( ((start.isAfter(iStart)) && (end.isBefore(iEnd))) || ((start.isBefore(iStart)) && end.isAfter(iEnd)) ) return true;
        if( (start.isBefore(iStart)) && (end.isAfter(iEnd)) ) return true;
        if( (start.isBefore(iStart) && (end.isBefore(iEnd))) ) return true;

        return false;*/

    }
    public boolean daysMatch(TimeInterval interval){

        if( !this.getDays().isPresent()  || !interval.getDays().isPresent() ) return false;

        for(DayOfWeek day : days)
            for(DayOfWeek iDay : interval.getDays().get()) // Checked above...
                if (day.equals(iDay)) return true;

        return false;
    }
    public Optional<TimeInterval> overlapWith(TimeInterval interval){

        if(!this.overlaps(interval)) return Optional.empty();

        LocalTime iStart = interval.getStart();
        LocalTime iEnd = interval.getEnd();

        LocalTime s;

        if(start.equals(iStart)){
            s = start;
        }
        else {
            s = start.isBefore(iStart) ? iStart : start;
        }

        LocalTime e;

        if(end.equals(iEnd)){
            e = end;
        }
        else {
            e = end.isBefore(iEnd) ? end : iEnd;

        }
        return Optional.of(new TimeInterval(s, e));

    }
    public boolean hasDays(){
        return false;
    }
    public static TimeInterval of(Event event){

        TimeInterval timeInterval = new TimeInterval(event.getStart(), event.getEnd());
        timeInterval.addDay(event.getDate().getDayOfWeek());

        return timeInterval ;
    }

    public String toString() {
        return start+" -> "+end;
    }

    public String toStringWithDays() {

        return "TimeInterval{" + "start=" + start + ", end=" + end + ", days=" + Arrays.toString(days) + '}';
    }

    public static void main(String[] args) {

        TimeInterval i0 = new TimeInterval(LocalTime.of(10,0), LocalTime.of(14,0));
        TimeInterval i1 = new TimeInterval(LocalTime.of(6,0), LocalTime.of(18,0));

        System.out.println(i0.overlaps(i1));

        i0.overlapWith(i1).ifPresent( (timeInterval)-> {System.out.println(timeInterval+"");});


        i0.getDays().ifPresent(dayOfWeeks->{System.out.println(dayOfWeeks);});
        i0.addDay(DayOfWeek.MONDAY);
        i0.getDays().ifPresent(consumer->{System.out.println(Arrays.toString(consumer));});


        TimeInterval lateStart = new TimeInterval(LocalTime.of(22,0), LocalTime.of(6,0));
        TimeInterval latePay = new TimeInterval(LocalTime.of(0,0), LocalTime.of(5,0));

        lateStart.overlapWith(latePay).ifPresent(System.out::println);

        System.out.println(lateStart.getMinutes());

        int time = 22-14;
        System.out.println((time) < 0 ? 24-Math.abs(time) : time);

    }
}
