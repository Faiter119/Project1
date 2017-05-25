/**
 * Created by Olav Husby on 01.07.2016.
 */

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventSaverApp extends Application { // TODO: 07.07.2016 cleanup

    public static void main(String[] args) {
        launch(args);
    }

    private static ArrayList<Event> events;
    private static Pay pay;

    private static BorderPane border;
    private static Stage stage;

    public void start(Stage theStage) {

        stage = theStage;
        events = (ArrayList<Event>) Manager.read().get();
        System.out.println(Manager.getJarFolder());
        if(events == null){
            events = External.noStorageFileFoundAlert(stage);

            //events = External.loadNewEvents(stage);

            if(events == null) {
                System.out.println("i give up");
                System.exit(-1); // just give up
            }
        }
        
        if(events.size() == 0) events = Manager.getDefaultEvents();

        border = new BorderPane();
        //border.setPadding(new Insets(20));

        border.setCenter(tableOfEventsPane());
        border.setTop(toolBar());
        //border.setRight(newEventPane());
        //border.setLeft(eventDetailsPane(table.getSelectionModel().getSelectedItem()));

        table.getSelectionModel().selectedIndexProperty().addListener( (event) -> {

            List<Event> selectedEvents = table.getSelectionModel().getSelectedItems();
            int countOfRowsSelected = selectedEvents.size(); //table.getSelectionModel().getSelectedIndices().size();

            if(countOfRowsSelected <= 0){

                border.setLeft(null);

                //stage.sizeToScene();
            }
            if(countOfRowsSelected == 1){

                border.setLeft(eventDetailsPane(table.getSelectionModel().getSelectedItem()));

               // stage.centerOnScreen();
                //stage.sizeToScene();

            }
            if(countOfRowsSelected > 1){

                border.setLeft(severalSelectedItemsPane(selectedEvents));

                //stage.centerOnScreen();
                //stage.sizeToScene();
            }
            stage.sizeToScene(); // FIXME: 07.07.2016 Jittering

        });
        Scene scene = new Scene(border);

        System.out.println(Paths.get(".").toAbsolutePath());

        scene.getStylesheets().add("F:/Programming/HTMLCSS/Java/TimeKeeper/style.css");
        stage.setScene(scene);
        stage.setOnCloseRequest( (event) -> Manager.getJarFolder().ifPresent((consumer) -> Manager.write(new File(consumer, "storage.txt"), events)) );
        stage.show();
    }
    public static ToolBar toolBar(){

        ToolBar toolBar = new ToolBar();

        toolBar.getItems().addAll(financeButton()/*, new Button("Events")*/);


        return toolBar;

    }
    private static Button financeButton(){

        Button button = new Button("Finance");
        button.setOnAction(event -> {
            stage.setScene(financeChangeScene());
            stage.sizeToScene();
            stage.show();
        });

        return button;
    }
    private static Scene financeChangeScene(){

        BorderPane borderPane = new BorderPane();


        borderPane.setRight(newTimeIntervalPane());

        return new Scene(borderPane);
    }
    private static Node newTimeIntervalPane(){

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10); gridPane.setHgap(10); gridPane.setPadding(new Insets(10));
        //
        DayOfWeek[] days = DayOfWeek.values();
        CheckBox[] weekDayCheckBoxes = new CheckBox[days.length];
        for(int i=0; i<days.length; i++){
            DayOfWeek day = days[i];
            CheckBox checkBox = new CheckBox(day.toString().charAt(0)+day.toString().substring(1).toLowerCase()); // MONDAY -> Monday
            checkBox.setOnAction(event -> {
                DayOfWeek d = DayOfWeek.valueOf(checkBox.getText().toUpperCase());
                if(checkBox.isSelected()) System.out.println(d);
            });
            weekDayCheckBoxes[i] = checkBox;

        }
       // Bindings.bindContent(map);
        VBox dayCheckBoxHBox = new VBox(weekDayCheckBoxes);
        dayCheckBoxHBox.setAlignment(Pos.CENTER_LEFT); dayCheckBoxHBox.setSpacing(10);
        //

        gridPane.add(dayCheckBoxHBox,4,2);
        VBox rightVBox = new VBox(dayCheckBoxHBox);
        rightVBox.setAlignment(Pos.CENTER); rightVBox.setSpacing(10); rightVBox.setPadding(new Insets(10));

        return gridPane;
    }

    private static TableView<Event> table;
    public static Pane tableOfEventsPane(){

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER); vBox.setPadding(new Insets(20)); vBox.setSpacing(20);
        vBox.setMaxWidth(500); vBox.setMinWidth(500);

        /*GridPane grid = new GridPane();
        grid.setVgap(20); grid.setHgap(20); */

        // Table
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // No dumb extra colomns
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setMinWidth(400); table.setMaxWidth(400);


        TableColumn<Event, LocalDate> date = new TableColumn<>("Date");
        date.setCellValueFactory( (data) -> new SimpleObjectProperty<>(data.getValue().getDate()) ); // "data" is a CellData object made my JavaFx containing the data in the cells

        TableColumn<Event, LocalTime> startTime = new TableColumn<>("Start");
        startTime.setCellValueFactory( (data) -> new SimpleObjectProperty<>(data.getValue().getStart())); // Need to use SimpleObjectProperty because javafx says so (-.-*)==\\7

        TableColumn<Event, LocalTime> endTime = new TableColumn<>("End");
        endTime.setCellValueFactory( (data) -> new SimpleObjectProperty<>(data.getValue().getEnd()));

        TableColumn<Event, String> desc = new TableColumn<>("Description");
        desc.setCellValueFactory( (data) -> new SimpleObjectProperty<>(data.getValue().getDescription()));

        table.getColumns().addAll(date, startTime, endTime, desc);

        table.getItems().addAll(events);
        //table.autosize();
        //table.getSelectionModel().select(0);

        // Table

        Label label = new Label("Events");
        label.setFont(new Font("Consolas",20));
        label.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(label, table, tableButtonsPane());

        return vBox;
    }
    public static Pane newEventPane(){

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER); vBox.setSpacing(20); vBox.setPadding(new Insets(20));
        GridPane grid = new GridPane();

        grid.setVgap(20); grid.setHgap(20); grid.setPadding(new Insets(20)); grid.setAlignment(Pos.CENTER);

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        datePicker.setEditable(false);

       /* TextField dateTextField = new TextField(LocalDate.now().toString());
            dateTextField.setTooltip(new Tooltip("Format is YYYY-MM-DD"));*/
        TextField startTextField = new TextField();
            startTextField.setTooltip(new Tooltip("Format is H, HH:MM, or H:MM | H = Hour(24-clock), M = Minute"));
        TextField endTextField = new TextField();
            endTextField.setTooltip(new Tooltip("Format is H, HH:MM, or H:MM | H = Hour(24-clock), M = Minute"));
        TextField descriptionTextField = new TextField();

        datePicker.setOnKeyPressed((event -> {
            if(event.getCode() == KeyCode.ENTER) startTextField.requestFocus();
        }));
        startTextField.setOnKeyPressed((event -> {
            if(event.getCode() == KeyCode.ENTER) endTextField.requestFocus();
        }));
        endTextField.setOnKeyPressed((event -> {
            if(event.getCode() == KeyCode.ENTER) descriptionTextField.requestFocus();
        }));

        // Button
        Button addNewEventButton = new Button("Add");
        addNewEventButton.setAlignment(Pos.CENTER);

        addNewEventButton.setOnAction((e)->{

            try{
                String startString = startTextField.getText();
                String endString = endTextField.getText();
                String description = descriptionTextField.getText();

                LocalDate date = datePicker.getValue();
                LocalTime start;
                LocalTime end;

                Event event = new Event(date);
                event.setDescription(description);

                if(!startString.trim().equals("")) {
                    event.setStart(parse(startString));
                }

                if(!endString.trim().equals("")) {
                    event.setEnd(parse(endString));
                }

                System.out.println(event+" got added!");
                events.add(event);
                table.getItems().add(event);
                clearAll(startTextField, endTextField, descriptionTextField);

            }
            catch (DateTimeParseException ex){
                datePicker.setValue(LocalDate.now());
            }
        });
        // Button
        descriptionTextField.setOnKeyPressed((event -> {
            if(event.getCode() == KeyCode.ENTER) addNewEventButton.requestFocus();
        }));
        addNewEventButton.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) addNewEventButton.fire();
        });

        grid.addRow(0, new Label("Date: ")/*dateTextField*/, datePicker);
        grid.addRow(1, new Label("Start (Optional): "), startTextField);
        grid.addRow(2, new Label("End (Optional): "), endTextField);
        grid.addRow(3, new Label("Description (Optional): "), descriptionTextField);

        Label label = new Label("New Event");
        label.setFont(new Font("Consolas",20));

        vBox.getChildren().addAll(label, grid, addNewEventButton);

        return vBox;
    }
    public static Pane eventDetailsPane(Event event){

        if(event == null) return null;

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER); vBox.setSpacing(20); vBox.setPadding(new Insets(20));

        GridPane grid = new GridPane();
        grid.setVgap(20); grid.setHgap(20); grid.setPadding(new Insets(20));

        // date, start, end, desc, date created, duration?

        TextField date = new TextField(event.getDate().toString());
        date.setEditable(false);

        TextField start = new TextField(event.getStart().toString());
        start.setEditable(false);

        TextField end = new TextField(event.getEnd().toString());
        end.setEditable(false);

        Duration d = event.getDuration();
        TextField duration = new TextField(d.toHours()+" hours and "+((d.toMinutes())-(60*d.toHours()))+" minutes.");
        duration.setEditable(false);

        TextArea desc = new TextArea(event.getDescription());
        desc.setEditable(false);

        TextField dateCreated = new TextField(event.getDateAdded().toString());
        dateCreated.setEditable(false);

        TextField createdBy = new TextField(event.getAddedBy());
        createdBy.setEditable(false);

        TextField history = new TextField(event.getHistory());
        history.setEditable(false);

        grid.addRow(0, new Label("Date: "), date);
        grid.addRow(1, new Label("Start: "), start);
        grid.addRow(2, new Label("End: "), end);
        grid.addRow(3, new Label("Duration: "), duration);
        grid.addRow(4, new Label("Description"), desc);
        grid.addRow(5, new Label("Created: "), dateCreated);
        grid.addRow(6, new Label("By: "), createdBy);
        grid.addRow(7, new Label("History"), history); // For safety

        Label label = new Label("Details");
        label.setFont(new Font("Consolas",20));

        vBox.getChildren().addAll(label, grid);

        return vBox;


    }

    public static Pane tableButtonsPane(){

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER); //vBox.setPadding(new Insets(20));
        vBox.setPrefWidth(500);

        // Buttons
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER); hBox.setSpacing(20); hBox.setPadding(new Insets(20));

        // New
            Button newEventButton = new Button("New");
            newEventButton.setOnAction(event -> {
                border.setRight(newEventPane());
                stage.sizeToScene();
                //stage.centerOnScreen();
            });
        // New

        // Edit
            Button editButton = new Button("Edit");
            editButton.setOnAction(event -> {

                Event currentEvent = table.getSelectionModel().getSelectedItem();

                if(currentEvent != null) {
                    border.setLeft(eventEditPane(table.getSelectionModel().getSelectedItem()));
                    stage.sizeToScene();
                    //stage.centerOnScreen();
                }

            });
        // Edit

        // Delete
            Label deleteLabel = new Label("Type \"delete\" to delete Event. This is final!");
            deleteLabel.setFont(new Font("Consolas",15));
            deleteLabel.setVisible(false);

            TextField deleteTextField = new TextField(); // Write "delete" to delete, security
            deleteTextField.setVisible(false);
            deleteTextField.setOnAction(event -> {

                if(deleteTextField.getText().equalsIgnoreCase("delete")){

                    ObservableList<Event> selectedEvents = table.getSelectionModel().getSelectedItems();
                    System.out.println(Arrays.toString(selectedEvents.toArray()));

                    for(Event e : selectedEvents) { // FIXME: 07.07.2016
                        System.out.println(e.toString());
                        events.remove(e);
                        table.getItems().remove(e);
                    }

                }
                deleteTextField.clear();
                deleteTextField.setVisible(false);
                deleteLabel.setVisible(false);
                table.requestFocus();
            });

            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction((event -> {

                deleteTextField.setVisible(true);
                deleteLabel.setVisible(true);
                deleteTextField.requestFocus();

            }));
        // Delete

        // Load
            Button loadButton = new Button("Load from file");
            loadButton.setOnAction(event -> {

                ArrayList<Event> newEvents = External.loadNewEvents(stage);
                if(newEvents != null) {

                    Manager.getJarFolder().ifPresent( (consumer) -> Manager.write(new File(consumer, "storage.txt"),events)); // dam son where'd you find this

                    table.getItems().removeAll(events);
                    table.getItems().addAll(newEvents);

                    stage.sizeToScene();
                    //stage.centerOnScreen();
                }
            });
        // Load

        // Backup
            Button backupButton = new Button("Backup to file");
            backupButton.setOnAction(event -> {
                External.makeBackup(stage, events);
            });
        // Backup

        hBox.getChildren().addAll(newEventButton, editButton, deleteButton, loadButton, backupButton);
        // Buttons

        vBox.getChildren().addAll(hBox, deleteTextField, deleteLabel);
        return vBox;

    }
    public static Pane eventEditPane(Event event){

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER); vBox.setSpacing(20); vBox.setPadding(new Insets(20));

        GridPane grid = new GridPane();
        grid.setVgap(20); grid.setHgap(20); grid.setPadding(new Insets(20)); grid.setAlignment(Pos.CENTER);

        // date, start, end, desc, date created,

        String dateOrig = event.getDate().toString();
        TextField date = new TextField(dateOrig);

        String startOrig = event.getStart().toString();
        TextField start = new TextField(startOrig);

        String endOrig = event.getEnd().toString();
        TextField end = new TextField(endOrig);

        String descOrig = event.getDescription();
        TextArea desc = new TextArea(descOrig);

        grid.addRow(0, new Label("Date: "), date);
        grid.addRow(1, new Label("Start: "), start);
        grid.addRow(2, new Label("End: "), end);
        grid.addRow(3, new Label("Description"), desc);

        Label label = new Label("Edit Event");
        label.setFont(new Font("Consolas",20));

        Button acceptButton = new Button("Accept");
        acceptButton.setOnAction(e -> {

            String dateNew = date.getText();
            String startNew = start.getText();
            String endNew = end.getText();
            String descNew = desc.getText();

            if(!dateOrig.equals(dateNew)){
                try {
                    event.setDate(LocalDate.parse(dateNew));
                } catch (DateTimeParseException ex){date.setText(dateOrig);}
            }
            if(!startOrig.equals(startNew)){
                event.setStart(parse(startNew));
            }
            if(!endOrig.equals(endNew)){
                event.setEnd(parse(endNew));
            }
            if(!descOrig.equals(descNew)){
                event.setDescription(descNew);
            }
            table.getItems().set(table.getSelectionModel().getSelectedIndex(), event);
            table.requestFocus();
            table.getSelectionModel().select(0);
            border.setLeft(eventDetailsPane(table.getSelectionModel().getSelectedItem()));
            stage.sizeToScene();
            //stage.centerOnScreen();
        });

        vBox.getChildren().addAll(label, grid, acceptButton);

        return vBox;

    }
    public static Pane severalSelectedItemsPane(List<Event> events){

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER); vBox.setSpacing(20); vBox.setPadding(new Insets(20));
        vBox.setMaxWidth(500);

        Label label = new Label("Several Events");
        label.setFont(new Font("Consolas",20));


        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setMaxHeight(Screen.getPrimary().getBounds().getHeight()/2);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        GridPane eventsGrid = new GridPane();

        for(int i=0; i<events.size(); i++){
            Event event = events.get(i);
            Duration d = event.getDuration();

            TextField date = new TextField(event.getDate().toString());
            date.setEditable(false);
            TextField time = new TextField(d.toHours()+" hours and "+((d.toMinutes())-(60*d.toHours()))+" minutes.");
            time.setEditable(false);
            //date.setPrefWidth(200);
            eventsGrid.addRow(i, date, time);
            //vBox.getChildren().add(new TextField(events[i].toString()));
        }


        //Duration total = events[1].getDuration();
        Duration d = Duration.ZERO;
        for(Event e : events){
            d = d.plus(e.getDuration());
        }
        scrollPane.setContent(eventsGrid);

        TextField duration = new TextField(d.toHours()+" hours and "+((d.toMinutes())-(60*d.toHours()))+" minutes.");
        duration.setEditable(false);


        TextField wageTextField = new TextField();
        wageTextField.setVisible(false); wageTextField.setEditable(false);

        BigDecimal wage = new BigDecimal(162.45*d.toHours());
        Button calculateWageButton = new Button("Calculate Wage");
        calculateWageButton.setOnAction(event -> {

            // TODO: 29.07.2016
            wageTextField.setVisible(true);
            wageTextField.setText(wage.doubleValue()+"// Todo fix");
        });

        HBox wageHBox = new HBox(calculateWageButton, wageTextField);
        wageHBox.setAlignment(Pos.CENTER); wageHBox.setSpacing(10);
        vBox.getChildren().addAll(label, scrollPane, duration, wageHBox);

        return vBox;
    }


    private static void clearAll(TextInputControl ... items){

        for(TextInputControl item : items){
            item.clear();
        }
    }

    /**
     * Takes badly formated time and makes it into localtime by magic
     */
    private static LocalTime parse(String time){
        time = time.trim();
        if(time.length() == 4) time = time.substring(0,2)+":"+time.substring(2); // changes 1430 -> 14:30
        else if(time.length() == 3) time = time.substring(0,1)+":"+time.substring(1); // changes 630 -> 6:30

        time = time.replaceAll("[ .]",":");
        time = time.replaceAll("-",":");
        time = time.replaceAll(" ",":");

        try {
            if (time.contains(":")) {

                if(time.indexOf(":") == 1) time = "0"+time;

                return LocalTime.parse(time);
            }
            else {

                return LocalTime.of(Integer.parseInt(time),0);
            }
        }
        catch (DateTimeException | NumberFormatException ex){
            return LocalTime.MIN;
        }
    }
}