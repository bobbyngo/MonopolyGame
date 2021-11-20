package Tests;

import Game.Rail;
import Game.Player;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

public class RailTest {
    Rail rail;
    Player player1;
    Player player2;

    @Before
    public void init() {
        rail = new Rail("South Game.Rail", 3, 150);
        // Set the Rail ownership for player 2
        player2 = new Player("player1", rail);
        player2.buyPrivateProperty(rail);
        //Let player 1 lands on the Rail
        player1 = new Player("player1", rail);
    }

    @org.junit.Test
    public void testCollectMoney() {
        int previousBalance = player1.getPlayerBalance();
        rail.collectMoney(player1);
        int currentBalance = player1.getPlayerBalance();
        assertEquals(previousBalance - rail.getRentAmount(), currentBalance);
    }

    @org.junit.Test
    public void testGetOwnedRailNum() {
        rail.getOwnedRailNum();
        assertEquals(1, rail.getOwnedRailNum());
    }

    @org.junit.Test
    public void testSell() {
        int previousBalance = player2.getPlayerBalance();
        player2.addMoney(rail.sell());
        int currBalance = player2.getPlayerBalance();
        assertEquals(previousBalance + 150/2, currBalance);
    }
}
