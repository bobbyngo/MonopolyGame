package Tests;
import Game.*;
import org.junit.Before;
import static org.junit.Assert.*;

public class BankPropertyTest {
    BankProperty bankProperty;
    Player player;

    @Before
    public void init() {
        player = new Player("player1", new BankProperty("Electric Tax", 5, 200));
        bankProperty = new BankProperty("Electric Tax", 5, 200);
    }

    @org.junit.Test
    public void testCollectMoney() {
        int previousBalance = player.getPlayerBalance();
        bankProperty.collectMoney(player);
        int currentBalance = player.getPlayerBalance();
        assertEquals(previousBalance - bankProperty.getTaxValue(), currentBalance);
    }
}
