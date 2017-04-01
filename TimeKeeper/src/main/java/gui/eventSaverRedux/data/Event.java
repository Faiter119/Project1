package gui.eventSaverRedux.data;

import javafx.util.converter.LocalDateTimeStringConverter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class Event implements Serializable{

    private static final long serialVersionUID = 1123685972764286834L;

    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDate dateAdded; // Impossible to affect
    private String addedBy;
    private String history;

    private String category;


    public Event(LocalDateTime startTime, LocalDateTime endTime, String category, String description) {
        this();
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.category = category;
    }

    public String getCategory() {

        return category;
    }

    public void setCategory(String category) {

        this.category = category;
    }

    public Event(){

        dateAdded = LocalDate.now();
        addedBy = System.getProperty("user.name");

        history = toString();
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
    public void setStart(LocalDateTime start){
        this.startTime = start;
        history += " - new Start: "+start;
    }
    public LocalDateTime getStart(){

        if(startTime == null) return LocalDateTime.MIN;

        return startTime;
    }
    public LocalDateTime getEnd(){

        if(endTime == null) return LocalDateTime.MIN;

        return endTime;
    }
    public void setEnd(LocalDateTime end){
        this.endTime = end;
        history += " - new End: "+end;
    }

    public boolean isPast(){return Period.between(LocalDate.now(), startTime.toLocalDate()).isNegative() && !isToday();}
    public boolean isFuture(){return !Period.between(LocalDate.now(), startTime.toLocalDate()).isNegative() && !isToday();}
    public boolean isToday(){
        return startTime.isEqual(LocalDateTime.now());
    }

    public String toString() {

        LocalDateTimeStringConverter converter = new LocalDateTimeStringConverter();
        if(startTime != null && endTime != null) {
            return  converter.toString(startTime) + " -> " + converter.toString(endTime);
        }
        return "";
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
