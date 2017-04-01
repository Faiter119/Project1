package gui.eventSaverRedux.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Created by OlavH on 31-Mar-17.
 */
public class TestController extends VBox{

    @FXML
    private TextField textField;

    public TestController(){

    }

    public void setText(String text){

        textField.setText(text);
    }

}
