package Tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import Game.*;

import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Testing class for monopoly
 * To verify:
 * - The Game.Player steps on 'Go To Jail'
 * - The Game.Player steps on:
 *      - owned Game.PrivateProperty (Game.Rail & Game.Business)
 *      - unowned Game.PrivateProperty (Game.Rail & Game.Business)
 *      - Game.BankProperty
 *      - Go To Jail
 * - The Game.Player passes the GO square
 * - The Game.Player moves k steps forward:
 */

public class MonopolyModelTest {

    private static int NUM_PLAYERS;
    private MonopolyModel model;
    private MonopolyGUIView view;
    private int []roll;
    //Game.Player p1 =new Game.Player("player4", new Game.Square("GO", 0));

    @Before
    public void init() {
        //For running the code, players array list cannot be empty
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("player1", new Square("GO", 0)));
        players.add(new Player("player2", new Square("GO", 0)));
        players.add(new Player("player3", new Square("GO", 0)));
        players.add(new Player("player4", new Square("GO", 0)));
        NUM_PLAYERS = players.size();
        //view = new MonopolyGUIView();
        model = new MonopolyModel(players, view);
    }

    @org.junit.Test
    public void testBuyBusiness(){
        model.purchaseProperty(new Business("Anarchy Acres", 1, 50));
        ArrayList<PrivateProperty> privatePropertyList = model.getCurrentPlayer().getPropertyList();
        assertEquals(1, privatePropertyList.size());
    }

    @org.junit.Test
    public void testBuyRail(){
        model.purchaseProperty(new Rail("South Game.Rail", 3, 150));
        ArrayList<PrivateProperty> privatePropertyList = model.getCurrentPlayer().getPropertyList();
        assertEquals(1, privatePropertyList.size());
    }

    @org.junit.Test
    public void testSellBusiness(){
        /*
        1. Give current Game.Player a Game.Business
            - assert that the Game.Business is owned
        2. Get the Game.Player's current cash amount
        3. Get the Game.Business' value and divide by 2
        4. Sell the Game.Business
        5. Assert that the player's cash amount is equal to:
            current cash - business val / 2
        6. Assert that the Game.Business is not owned
         */
        /*
        Game.Player p = model.getCurrentPlayer();
        model.purchaseProperty();
        p.buyPrivateProperty();
         */
        // 1.
        Player p = model.getCurrentPlayer();
        Business business = (Business) model.getBoard().getSQUARE(15);
        p.setCurrLocation(business);
        model.purchaseProperty(business);
        //p.buyPrivateProperty(business);
        Assert.assertEquals(p.getCurrLocation(), business);
        assertTrue(business.isOwned());
        Assert.assertEquals(p, business.getOwner());

        // 2.
        int startCash = p.getPlayerBalance();
        // 3.
        int sellPrice = (int)(business.getPrice() / 2);

        // 4.
        model.sellProperty(business);
        assertFalse(business.isOwned());
        // 5.
        int currCash = p.getPlayerBalance();
        assertEquals(currCash, startCash + sellPrice);
    }

    @org.junit.Test
    public void testSellRail(){
        // 1.
        Player p = model.getCurrentPlayer();
        Rail rail = (Rail) model.getBoard().getSQUARE(11);
        p.setCurrLocation(rail);
        model.purchaseProperty(rail);
        //p.buyPrivateProperty(rail);
        Assert.assertEquals(p.getCurrLocation(), rail);
        assertTrue(rail.isOwned());
        Assert.assertEquals(p, rail.getOwner());

        // 2.
        int startCash = p.getPlayerBalance();
        // 3.
        int sellPrice = (int)(rail.getPrice() / 2);

        // 4.
        model.sellProperty(rail);
        assertFalse(rail.isOwned());
        // 5.
        int currCash = p.getPlayerBalance();
        assertEquals(currCash, startCash + sellPrice);
    }

    @org.junit.Test
    public void testPayingTax(){
        //Balance before paying fee
        int currentBalance = model.getCurrentPlayer().getPlayerBalance();
        model.getCurrentPlayer().setCurrLocation(new BankProperty("Electric Tax", 5, 200));
        model.payFee();

        Assert.assertEquals(currentBalance - 200, model.getCurrentPlayer().getPlayerBalance());
    }

    @org.junit.Test
    public void testPlayerMoving(){
        // This test will work only if the player starts at index 0 to move to the k value
        model.rollDie();
        int newIndex = model.getDie().getTotal();

        model.moveCurrentPlayer();

        Assert.assertEquals(newIndex, model.getCurrentPlayer().getCurrLocation().getIndex());
    }

    @org.junit.Test
    public void testPassingGo() {
        Player p = model.getCurrentPlayer();
        p.setCurrLocation(new Business("Snobby Shores", 37, 120));
        int currentBalance = p.getPlayerBalance();

        model.rollDie();
        model.moveCurrentPlayer();
        int afterPassingGoBalance = p.getPlayerBalance();

        assertEquals(currentBalance + 200, afterPassingGoBalance);
    }

    @org.junit.Test
    public void testGoToJailSquare() {
        Player p = model.getCurrentPlayer();
        Square goToJail = model.getBoard().getGoToJail();
        p.setCurrLocation(goToJail);
        Assert.assertEquals(p.getCurrLocation(), goToJail);
        assertFalse(p.isInJail());

        boolean onGoToJail = model.currentPlayerIsOnGoToJail();
        assertTrue(onGoToJail);

        model.sendCurrentPlayerToJail();
        Square prison = model.getBoard().getJail();
        Assert.assertEquals(prison, p.getCurrLocation());
    }

    @Test
    public void testServeJailTime() {
        testGoToJailSquare();
        Player prisonPlayer = model.getCurrentPlayer();
        Player currPlayer = model.getCurrentPlayer();

        for (int i=0; i<2; i++) {
            for (int j=0; j < NUM_PLAYERS; j++) {
                if (currPlayer.isInJail()) {
                    currPlayer.serveJailTime();
                }
                currPlayer = model.getNextPlayer();
            }
        }
        Assert.assertEquals(prisonPlayer, model.getCurrentPlayer());

        assertTrue(prisonPlayer.isInJail());
        boolean hasServedTime = prisonPlayer.serveJailTime();
        assertTrue(hasServedTime);
        assertFalse(prisonPlayer.isInJail());



    }

}