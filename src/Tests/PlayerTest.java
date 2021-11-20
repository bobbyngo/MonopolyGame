package Tests;

import Game.*;
import org.junit.Before;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class PlayerTest {
    private Player player;

    @Before
    public void init() {
        player = new Player("player1", new Square("GO", 0));
    }

    @org.junit.Test
    public void testBuyPrivateProperty() {
        player.buyPrivateProperty(new Business("Anarchy Acres", 1, 50));
        ArrayList<PrivateProperty> privatePropertyList = player.getPropertyList();
        assertEquals(1, privatePropertyList.size());
    }

    @org.junit.Test
    public void testBuyRail(){
        player.buyPrivateProperty(new Rail("South Game.Rail", 3, 150));
        ArrayList<PrivateProperty> privatePropertyList = player.getPropertyList();
        assertEquals(1, privatePropertyList.size());
    }

    @org.junit.Test
    public void testSellBusiness(){

        Business business = new Business("Lazy Lagoon", 15, 80);
        player.buyPrivateProperty(business);
        player.removeProperty(business);
        ArrayList<PrivateProperty> privatePropertyList = player.getPropertyList();
        assertEquals(0, privatePropertyList.size());

    }

    @org.junit.Test
    public void testSellRail(){
        Rail rail = new Rail("South Game.Rail", 3, 150);
        player.buyPrivateProperty(rail);
        player.removeProperty(rail);
        ArrayList<PrivateProperty> privatePropertyList = player.getPropertyList();
        assertEquals(0, privatePropertyList.size());
    }
}
