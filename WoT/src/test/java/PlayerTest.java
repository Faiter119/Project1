import backend.data.Player;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by OlavH on 27-Feb-17.
 */
public class PlayerTest {

    static Player player;

    @BeforeClass
    public static void init(){

        player = new Player("OlavH96@gmail.com");
        player.setUserName("Faiter119");
        player.setClan("GAX");
        player.addCredits(100000);
        player.addGold(5000);
    }
    @Before
    public void before(){

        player.setCredits(100000);
        player.setGold(5000);

    }


    @Test(expected = IllegalArgumentException.class)
    public void testSpendCredits(){

        player.removeCredits(10000);

        assertEquals(90000, player.getCredits());

        player.removeCredits(100000); // too much

        assertEquals(90000, player.getCredits());

        player.removeCredits(-1);

        player.removeCredits(0);

        assertEquals(90000, player.getCredits());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCredits(){

        player.addCredits(100);

        assertEquals(100100, player.getCredits());

        player.addCredits(-1);

        player.addCredits(0);

        assertEquals(100100, player.getCredits());

    }

}

