package Files;

import data.Person;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OlavH on 20-Mar-17.
 */
public class FileSingleObjectWriter {

    private Path path;
    private ObjectOutputStream objectOutputStream;


    public FileSingleObjectWriter(Path path) {

        this.path = path;
        initializeWriter();
    }
    private void initializeWriter() {

        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(path.toFile()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }


    public ObjectOutputStream getObjectOutputStream() {

        return objectOutputStream;

    }


    public <T> void writeObject(T object) {

        if (objectOutputStream == null) initializeWriter();

        try {
            objectOutputStream.writeObject(object);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void main(String[] args) throws IOException, ClassNotFoundException {


        List<Person> personList = new ArrayList<>();

        personList.add(new Person("Olav H", 98765432));
        personList.add(new Person("Magnus", 56178912));

        FileSingleObjectWriter objectFileHandler = new FileSingleObjectWriter(Paths.get("C:/Users/OlavH/IdeaProjects/Project1/FileReader/src/main/resources/object.txt"));

        objectFileHandler.writeObject(personList);




    }

}
