import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

public class External {

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T noStorageFileFoundAlert(Stage stage){

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("No storage-file found!");
        alert.setContentText("Choose your option.");

        ButtonType newButton = new ButtonType("New file");
        ButtonType loadButton = new ButtonType("Load file");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(newButton, loadButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == newButton){

            System.out.println("New file..");


            File newFile = Manager.newStorageFile().get();


            Manager.newStorageFile().ifPresent( (file) -> Manager.readFile(file));

            return (T) Manager.readFile(newFile).get();

        } else if (result.get() == loadButton) {

            System.out.println("Load file..");

            return (T) loadNewEvents(stage);

        }
        else {
            return null;
        }
    }
    public static void makeBackup(Stage stage, ArrayList<Event> events){

        DirectoryChooser chooser = new DirectoryChooser();
        Manager.getJarFolder().ifPresent((file)-> chooser.setInitialDirectory(file));

        File selectedDir = chooser.showDialog(stage);

        if(selectedDir == null){
            Manager.getJarFolder().ifPresent((file) -> Manager.writeBackup(file, events));
        }
        else {
            Manager.writeBackup(selectedDir, events);
        }
    }
    public static ArrayList<Event> loadNewEvents(Stage stage){

        FileChooser chooser = new FileChooser();

        Manager.getJarFolder().ifPresent((file)-> chooser.setInitialDirectory(file));

        File loadedFile = chooser.showOpenDialog(stage);

        if(loadedFile == null) return null;

        return (ArrayList<Event>) Manager.readFile(loadedFile).get();
    }
}
