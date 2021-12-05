package Tests;

import Game.Business;
import Game.Player;
import Game.PrivateProperty;
import org.junit.Before;
import static org.junit.Assert.*;

public class PrivatePropertyTest {

    private PrivateProperty privateProperty;

    @Before
    public void init() {
        privateProperty = new Business("Anarchy Acres", 1,50);
    }

    @org.junit.Test
    public void testRemoveOwner(){
        Player player = new Player("Player 1", new PrivateProperty("Anarchy Acres", 1,50));
        privateProperty.setOwner(player);
        privateProperty.removeOwner();
        assertFalse(privateProperty.isOwned());
    }


    @org.junit.Test
    public void testSetOwner(){
        Player player = new Player("Player 1", new PrivateProperty("Anarchy Acres", 1,50));

        privateProperty.setOwner(player);
        assertTrue(privateProperty.isOwned());

    }
}
