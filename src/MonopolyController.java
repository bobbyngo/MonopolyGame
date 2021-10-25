import java.util.ArrayList;

/**
 * @author Gabriel Benni Kelley Evensen 101119814
 *
 * The controller for the game of monopoly; handles the players, board, banks, DIE, and the current player
 */
public class MonopolyController {
    private ArrayList<Player> players;
    private Board board;
    private Bank bank;
    private Dice die;
    private Player currentPlayer;

    public MonopolyController(ArrayList<Player> players) {
        this.players = new ArrayList<>(players);
        this.board = new Board();
        this.bank = new Bank();
        this.die = new Dice();
        this.currentPlayer = this.players.get(0);
    }

    /**
     * Method to sell the current rail property and returns half the worth of the property to the seller.
     * Method overloading used as both Rail and Business are polymorphs when selling (b/c business may have houses/hotels)
     *
     * @param property, the rail property being sold
     */
    public void sellProperty(Rail property) {
        this.currentPlayer.addMoney(property.sell());
    }

    /**
     * Method to sell the current business property and returns half the worth of the property to the seller.
     * Method overloading used as both Rail and Business are polymorphs when selling (b/c business may have houses/hotels)
     *
     * @param property, the business property being sold
     */
    public void sellProperty(Business property) {
        this.currentPlayer.addMoney(property.sell());
    }

    /**
     * Method to purchase the current PrivateProperty, or a house/hotel for the current Business property (cast from the PrivateProperty).
     *
     * If the currentPlayer already owns a full colour set of Business Properties, then they automatically buy one house, iff they already have ...
     * ...four houses on one square, those houses get replaced with one hotel (one hotel is worth more than four houses)
     *
     * @param property, the privateproperty property being sold
     */
    public void purchaseProperty(PrivateProperty property) {
        if (property instanceof Rail) {
            this.currentPlayer.buyPrivateProperty(property);                                            //This adds property and removes money for purchase
            this.bank.addMoney(property.getPrice());                                                    //This adds the money from the purchase to the bank
        } else if (property instanceof Business && this.currentPlayer.isOwningColorGroup()) {           //The property type must be Business; if the currentPlayer owns the full colour set, then:-
            Business businessProperty = (Business) property;                                            //Cast privateproperty type to Business for manipulation

            if (businessProperty.getNumHouse() < 4) {                                                   //If the Business Property has less than the maximum allowed houses, 5
                businessProperty.buyHouse();                                                            //Increments house count by one
                this.currentPlayer.removeMoney((int) (100 + businessProperty.getPrice() * 0.1));        //Removes price of one house from currentPlayers purse
                this.bank.addMoney((int) (100 + businessProperty.getPrice() * 0.1));
            } else if (businessProperty.getNumHouse() == 4) {                                           //Else if the Business Property has four houses, replace those four with a hotel, worth more than four houses
                businessProperty.removeHouses();                                                        //Removes all houses from the current property
                businessProperty.buyHotel();                                                            //Puts one hotel on the property
                this.currentPlayer.removeMoney((int) (100 + businessProperty.getPrice() * 0.6));        //Removes price of one hotel from currentPlayers purse
                this.bank.addMoney((int) (100 + businessProperty.getPrice() * 0.6));
            }
        } else if (property instanceof Business && !this.currentPlayer.isOwningColorGroup()) {          //Else if the property is Business Property and the currentPlayer does not own a full set; they may buy it
            this.currentPlayer.buyPrivateProperty(property);                                            //This adds property and removes money for purchase
            this.bank.addMoney(property.getPrice());                                                    //This adds the money from the purchase to the bank
        } else {
            System.err.println("You are unable to buy anything on this square");
        }
        //For buying property: Check if the class is Rail by: .instanceof(Rail)
        // If yes, we do not need to check the full set of color to buy rail
        // I agree, if .instanceof(Rail) works as intended then we do not need to use color code "white" for rail
    }
}





