import data.Person;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

/**
 * Created by OlavH on 20-Mar-17.
 */
public class FileSingleObjectTest {

    static String path = "C:\\Users\\OlavH\\IdeaProjects\\Project1\\FileReader\\src\\main\\resources\\testObject.txt";


    static FileSingleObjectWriter writer;
    static FileSingleObjectReader reader;

    @BeforeClass
    public static void before(){

        writer = new FileSingleObjectWriter(Paths.get(path));
        reader = new FileSingleObjectReader(Paths.get(path));
    }

    @Test
    public void test_written_object_is_preserved() {

        Person person = new Person("olavH", 56789);

        writer.writeObject(person);

        Object o = reader.readObject();

        System.out.println(o);

        assertTrue(person.equals(o));

    }


}
