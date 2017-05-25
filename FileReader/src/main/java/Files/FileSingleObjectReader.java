package Files;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by OlavH on 20-Mar-17.
 */
public class FileSingleObjectReader {

    private Path path;
    private ObjectInputStream objectInputStream;


    public FileSingleObjectReader(Path path) {

        this.path = path;
        initializeReader();
    }
    private void initializeReader() {

        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(path.toFile()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public <T> Object readObject(){

        if (objectInputStream == null) initializeReader();


        try {
            return objectInputStream.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }



    public static void main(String[] args) throws IOException, ClassNotFoundException {

        FileSingleObjectReader objectFileHandler = new FileSingleObjectReader(Paths.get("/home/faiter/IntelliJ/Projects/Project1/FileReader/src/main/resources/object.txt"));

        System.out.println(String.valueOf(objectFileHandler.readObject()));



    }

}
