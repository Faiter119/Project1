package gui.eventSaverRedux.gui;/**
 * Created by OlavH on 31-Mar-17.
 */

import gui.eventSaverRedux.controllers.NewEventPaneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class EventSaverReduxApp extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        Pane root = FXMLLoader.load(getClass().getClassLoader().getResource("eventSaverRedux/eventSaverRedux.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("eventSaverRedux/newEventPane.fxml"));

        VBox newEventPane = loader.load();
        NewEventPaneController newEventPaneController = loader.getController();

        System.out.println(newEventPaneController.getChildren());


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        stage.setMinHeight(screenSize.getHeight()/2); stage.setMinWidth(screenSize.getWidth()/2);
        stage.setTitle("Event Saver REDUX");
        stage.setScene(new Scene(root));
        stage.show();
    }
}