import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

public class Event implements Serializable{

    private static final long serialVersionUID = 1123685972764286833L;

    private LocalDate date;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate dateAdded; // Impossible to affect
    private String addedBy;
    private String history;

    public Event(LocalDate date){
        this.date = date;
        dateAdded = LocalDate.now();
        addedBy = System.getProperty("user.name");

        history = toString();
    }

    public LocalDate getDate(){
        return date;
    }
    public void setDate(LocalDate date){
        this.date = date;
        history += " - new Date: "+date;
    }
    public LocalDate getDateAdded(){return dateAdded;}
    public String getAddedBy(){return addedBy;}
    public String getHistory(){return history;}

    public String getDescription(){

        if(description != null) return description;

        return "";
    }
    public void setDescription(String description){
        this.description = description;
    }
    public Duration getDuration(){

        if(startTime != null && endTime != null){
            return Duration.between(startTime, endTime);
        }
        return Duration.ZERO;
    }
    public void setStart(LocalTime start){
        this.startTime = start;
        history += " - new Start: "+start;
    }
    public LocalTime getStart(){

        if(startTime == null) return LocalTime.MIN;

        return startTime;
    }
    public LocalTime getEnd(){

        if(endTime == null) return LocalTime.MIN;

        return endTime;
    }
    public void setEnd(LocalTime end){
        this.endTime = end;
        history += " - new End: "+end;
    }

    public boolean isPast(){
        return Period.between(LocalDate.now(), date).isNegative() && !isToday();
    }
    public boolean isFuture(){
        return !Period.between(LocalDate.now(), date).isNegative() && !isToday();
    }
    public boolean isToday(){
        return date.isEqual(LocalDate.now());
    }

    public String toString() {

        if(startTime != null && endTime != null) {
            return date.toString() + " : " + startTime + " -> " + endTime;
        }
        return date.toString();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        /*Event event = new Event(LocalDate.of(2016,7,1));
        event.setDescription("Olav was born.");

        event.setStart(LocalTime.of(12,0));
        event.setEnd(LocalTime.now());
        System.out.println(event.isPast());
        System.out.println(event.isFuture());
        System.out.println(event.isToday());

        System.out.println(event);*/


        String path = "./TimeKeeper/resources/BACKUP_2017-03-16.txt/";

        ObjectInputStream stream = new ObjectInputStream(new FileInputStream(path));

        Object o = stream.readObject();

        System.out.println(o);

    }
}
