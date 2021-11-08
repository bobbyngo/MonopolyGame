import org.junit.Before;

import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Testing class for monopoly
 * To verify:
 * - The Player steps on 'Go To Jail'
 * - The Player steps on:
 *      - owned PrivateProperty (Rail & Business)
 *      - unowned PrivateProperty (Rail & Business)
 *      - BankProperty
 *      - Go To Jail
 * - The Player passes the GO square
 * - The Player moves k steps forward:
 *      - w/ loop around back to 0
 *      - w/o loop around
 * - The Player steps on 'Free Parking Lot'
 */

public class MonopolyTest{
    private MonopolyController controller;
    private int []roll;
    //Player p1 =new Player("player4", new Square("GO", 0));

    @Before
    public void init() {
        //For running the code, players array list cannot be empty
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("player1", new Square("GO", 0)));
        players.add(new Player("player2", new Square("GO", 0)));
        players.add(new Player("player3", new Square("GO", 0)));
        players.add(new Player("player4", new Square("GO", 0)));

        controller = new MonopolyController(players);
    }

    @org.junit.Test
    public void testBuyBusiness(){
        controller.purchaseProperty(new Business("Anarchy Acres", 1, "red",50));
        ArrayList<PrivateProperty> privatePropertyList = controller.getCurrentPlayer().getPropertyList();
        assertEquals(1, privatePropertyList.size());
    }

    @org.junit.Test
    public void testBuyRail(){
        controller.purchaseProperty(new Rail("South Rail", 3, 150));
        ArrayList<PrivateProperty> privatePropertyList = controller.getCurrentPlayer().getPropertyList();
        assertEquals(1, privatePropertyList.size());
    }

    @org.junit.Test
    public void testSellBusiness(){

    }

    @org.junit.Test
    public void testSellRail(){

    }

    @org.junit.Test
    public void testPayingTax(){
        //Balance before paying fee
        int currentBalance = controller.getCurrentPlayer().getPlayerBalance();
        controller.getCurrentPlayer().setCurrLocation(new BankProperty("Electric Tax", 5, 200));
        controller.payFee();

        assertEquals(currentBalance-200, controller.getCurrentPlayer().getPlayerBalance());
    }

    @org.junit.Test
    public void testPlayerMoving(){
        // This test will work only if the player starts at index 0 to move to the k value
        controller.rollDie();
        int newIndex = controller.getDie().getTotal();

        controller.moveCurrentPlayer();

        assertEquals(newIndex, controller.getCurrentPlayer().getCurrLocation().getIndex());
    }
}
