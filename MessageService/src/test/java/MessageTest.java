import data.Message;
import org.junit.Test;

/**
 * Created by faiter on 8/9/17.
 */
public class MessageTest {

    @Test
    public void test(){

        Message<String> stringMessagee = new Message("Dette er en melding");

        Message<Integer> intMessage = new Message<>(42);

        System.out.println(stringMessagee.getMessage());
        System.out.println(intMessage.getMessage());

    }

}
