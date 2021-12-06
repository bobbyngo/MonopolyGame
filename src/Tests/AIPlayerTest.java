package Tests;

import Game.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AIPlayerTest {

    //private MonopolyModel model;
    private AIPlayer ai;
    private Business property;
    @Before
    public void setUp() throws Exception {
        property = new Business("Property", 5, 500);
        ai = new AIPlayer("AI", property);
        //model = new MonopolyModel();
    }

    @Test
    public void autoplay() {
        assertFalse(ai.getPropertyList().contains(property));
        ai.autoplay();
        assertTrue(ai.getPropertyList().contains(property));
    }

    @Test
    public void sellPropertiesUntilMin() {

        // initial cash: 1230
        // after autoplay: 730
        // expected after selling: 980
        // expected after buying: 80
        assertFalse(ai.getPropertyList().contains(property));
        ai.autoplay();
        assertTrue(ai.getPropertyList().contains(property));
        assertEquals(ai.getPlayerBalance(), 730);

        Player pl1 = new Player("enemy", null);
        Business b = new Business("p1", 6, 9800);
        b.setOwner(pl1);

        ai.setCurrLocation(b);
        ai.autoplay();

        assertFalse(ai.getPropertyList().contains(property));
        assertEquals(ai.getPlayerBalance(), 980);
    }
}