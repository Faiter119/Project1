package gui.eventSaverRedux.controllers;

import gui.eventSaverRedux.data.Event;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import utils.ArrayRange;
import utils.ArrayUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class NewEventPaneController extends VBox{

    @FXML
    private DatePicker newEventDatePicker;
    @FXML
    private ComboBox<Integer> newEventStartHourComboBox;
    @FXML
    private ComboBox<Integer> newEventStartMinuteComboBox;
    @FXML
    private ComboBox<Integer> newEventEndHourComboBox;
    @FXML
    private ComboBox<Integer> newEventEndMinuteComboBox;
    @FXML
    private ComboBox<String> newEventCategoryComboBox;
    @FXML
    private TextField newEventDescription;
    @FXML
    private Button newEventAddButton;
    
    public NewEventPaneController() {

    }
    

    @FXML
    void onnewEventAddButtonAction(ActionEvent actionEvent) {

        LocalDate localDate = newEventDatePicker.getValue();
        Integer startHour = newEventStartHourComboBox.getValue();
        Integer startMinute = newEventStartMinuteComboBox.getValue();
        Integer endHour = newEventEndHourComboBox.getValue();
        Integer endMinute = newEventEndMinuteComboBox.getValue();
        String category = newEventCategoryComboBox.getValue();
        String desc = newEventDescription.getText();

        LocalDateTime start = LocalDateTime.of(localDate, LocalTime.of(startHour, startMinute));
        LocalDateTime end = LocalDateTime.of(localDate, LocalTime.of(endHour, endMinute));


        Event event = new Event(start, end, category, desc);

        System.out.println(event);
    }
    @FXML
    public void initialize(){

        // DATEPICER
        String pattern = "yyyy-MM-dd";
        newEventDatePicker.setPromptText(pattern.toLowerCase());

        newEventDatePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        newEventDatePicker.setValue(LocalDate.now());



        // DROPDOWNS
        newEventStartHourComboBox.getItems().addAll(ArrayUtil.toArray(ArrayRange.intRange(0, 23)));
        newEventStartMinuteComboBox.getItems().addAll(ArrayUtil.toArray(ArrayRange.intRange(1, 59)));

        newEventEndHourComboBox.getItems().addAll(ArrayUtil.toArray(ArrayRange.intRange(0, 23)));
        newEventEndMinuteComboBox.getItems().addAll(ArrayUtil.toArray(ArrayRange.intRange(1, 59)));

        newEventStartMinuteComboBox.setValue(0);
        newEventStartHourComboBox.setValue(LocalTime.now().getHour()-9); // 8 hour work day

        newEventEndMinuteComboBox.setValue(0);
        newEventEndHourComboBox.setValue(LocalTime.now().getHour()-1); // takes 1 hour to get home maybe

        /*
         * CATEGORIES
         */
        newEventCategoryComboBox.setItems(FXCollections.observableArrayList(Arrays.asList(
                "HLP",
                "SS"
        )));
        newEventCategoryComboBox.getSelectionModel().select(0);
        this.setMinWidth(500); // So things dont get cut off, probably not the best way
    }

}