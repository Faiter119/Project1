package gui.eventSaverRedux.controllers;

import gui.eventSaverRedux.data.Event;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class EventSaverReduxController {

    @FXML
    private TableView<Event> eventTableView;

    @FXML
    public void initialize() {

        System.out.println("EXECUTE ORDER 66");

        // Table

        Event e1 = new Event();
        e1.setStart(LocalDateTime.now());
        e1.setEnd(LocalDateTime.now().plusHours(5));

        Event e2 = new Event();
        e2.setStart(LocalDateTime.now().minusDays(1));
        e2.setEnd(e2.getStart().plusHours(8));

        List<Event> testEvents = Arrays.asList(e1, e2);

        eventTableView.setItems(FXCollections.observableArrayList(testEvents));

        TableColumn<Event, String> dateColomn = new TableColumn<>("Date");
        dateColomn.setCellValueFactory(p ->
                new SimpleStringProperty(p.getValue().getStart().toLocalDate().toString()));

        TableColumn<Event, String> startColomn = new TableColumn<>("Start");
        startColomn.setCellValueFactory(p ->{

            String format = DateTimeFormatter.ofPattern("HH:mm").format(p.getValue().getStart().toLocalTime());

            return new SimpleStringProperty(format);

        });

        TableColumn<Event, String> endColomn = new TableColumn<>("End");
        endColomn.setCellValueFactory(p ->{
            String format = DateTimeFormatter.ofPattern("HH:mm").format(p.getValue().getEnd().toLocalTime());
            return new SimpleStringProperty(format);
        });


        TableColumn<Event, String> categoryColomn = new TableColumn<>("Category");
        categoryColomn.setCellValueFactory(p ->
                new SimpleStringProperty(p.getValue().getCategory()));

        TableColumn<Event, String> descColomn = new TableColumn<>("Description");
        descColomn.setCellValueFactory(p ->
                new SimpleStringProperty(p.getValue().getDescription()));

        eventTableView.getColumns().setAll(dateColomn, startColomn, endColomn, categoryColomn, descColomn);
    }
}