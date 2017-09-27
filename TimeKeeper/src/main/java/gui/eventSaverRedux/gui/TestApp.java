/*
package gui.eventSaverRedux.gui;*/
/**
 * Created by OlavH on 31-Mar-17.
 *//*


import gui.eventSaverRedux.controllers.TestController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TestApp extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("eventSaverRedux/test.fxml"));

        VBox vbox = loader.load();
        System.out.println(vbox);

        TestController testController = loader.getController();
        testController.setText("Ost!");

        stage.setScene(new Scene(vbox));
        stage.show();
    }
}
*/
