package Tests;
import Game.*;
import org.junit.Before;
import static org.junit.Assert.*;

public class BusinessTest {
    Business business;
    Player player1;
    Player player2;

    @Before
    public void init() {
        business = new Business("Anarchy Acres", 1,50);

        // Set the ownership for player 2
        player2 = new Player("player1", business);
        player2.buyPrivateProperty(business);

        //Let player 1 lands on the property
        player1 = new Player("player1", business);
    }

    @org.junit.Test
    public void testCollectMoney() {

        int previousBalance = player2.getPlayerBalance();
        business.collectMoney(player1);
        int currBalance = player2.getPlayerBalance();

        assertEquals(previousBalance + 50 * 0.1, currBalance, 0);
    }

    @org.junit.Test
    public void testSell() {
        int previousBalance = player2.getPlayerBalance();
        player2.addMoney(business.sell());
        int currBalance = player2.getPlayerBalance();
        assertEquals(previousBalance + 50/2, currBalance);
    }
}
