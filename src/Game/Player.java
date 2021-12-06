package Game;

import javax.swing.*;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Player implements RoleAPI {
    /**
     * Author: Ngo Huu Gia Bao
     * 101163137
     */

    private String name;
    private int playerBalance;
    private boolean inJail;
    private int turnsInJail;
    private int turnsPlayed;
    private Square currLocation;
    private HashMap<Integer, Integer> playerWallet;
    private ArrayList<PrivateProperty> propertyList;

    /**
     * Constructor for Game.Player class
     * @param name
     * @param currLocation
     */
    public Player(String name, Square currLocation) {
        this.name = name;

        this.turnsInJail = 0;
        this.turnsPlayed = 0;
        this.playerWallet = new HashMap<>();

        this.inJail = false;
        this.currLocation = currLocation;
        this.propertyList = new ArrayList<>();
        this.playerBalance = 1230;
    }

    public Player(String name, int playerBalance, boolean inJail, int turnsInJail, int turnsPlayed, Square currLocation) {
        this.name = name;

        this.turnsInJail = turnsInJail;
        this.turnsPlayed = turnsPlayed;
        //this.playerWallet = new HashMap<>();

        this.inJail = inJail;
        this.currLocation = currLocation;
        this.propertyList = new ArrayList<>();
        this.playerBalance = playerBalance;
    }


    /**
     * This method will let the player buy Game.PrivateProperty. If the Game.Player can buy the property,
     * it will call the addProperty method and set the owner of that Property
     * @param property
     */
    public void buyPrivateProperty(PrivateProperty property) {
        if (!property.isOwned()) {
            this.removeMoney(property.getPrice());
            this.addPropertyList(property);
            property.setOwner(this);
        } else {
            System.out.println("This property is owned");
        }
    }

    /**
     * This method will add the Property to the Game.Player's propertyList
     * @param property
     */
    public void addPropertyList(PrivateProperty property) {
        if (!propertyList.contains(property)) {
            this.propertyList.add(property);
        } else {
            System.out.println("You have this property already");
        }
    }

    /**
     * This method will remove Property to the Game.Player's propertyList
     * @param property
     */
    public void removeProperty(PrivateProperty property) {
        this.propertyList.remove(property);
        property.removeOwner();
    }


    public boolean isInJail() {
        return this.inJail;
    }

    /**
     * This method will add the money from the Game.Player balance
     * @param amount
     */
    @Override
    public void addMoney(int amount) {
        playerBalance += amount;
    }

    /**
     * This method will remove the money from the Game.Player balance
     * @param amount
     */
    @Override
    public void removeMoney(int amount) {
        if (playerBalance > amount) {
            playerBalance -= amount;
        } else {
            System.out.println("Your balance is not enough");
        }
    }

    /**
     * Method to return the number of bills in a persons wallet
     * @return String
     * @author Gabriel Benni Kelley Evensen 101119814
     */
    @Override
    public String walletToString() {
        String s = this.name + " has:- \n";
        for (Map.Entry<Integer, Integer> entry : this.playerWallet.entrySet()) {
            s.concat(entry.getValue() + "x $" + entry.getKey() + "\n");

        }
        return s;
    }

    /**
     * This method will check whether the playerBalance is < 0
     * @return boolean
     */
    public boolean isBankrupt() {
        if (playerBalance < 0) {
            return true;
        }
        return false;
    }

    /**
     * Getter for name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for playerBalance
     * @return int
     */
    public int getPlayerBalance() {
        return playerBalance;
    }

    /**
     * Getter for currLocation
     * @return Game.Square
     */
    public Square getCurrLocation() {
        return currLocation;
    }

    /**
     * Getter for propertyList
     * @return ArrayList
     */
    public ArrayList<PrivateProperty> getPropertyList() {
        return propertyList;
    }


    public void setCurrLocation(Square currLocation) {
        this.currLocation = currLocation;
    }

    public void getInJail() {
        this.inJail = true;
    }


    /**
     * Method handler to check if a player is to remain in jail for this turn (returns true); or if a player should be allowed to move this turn (returns false)
     *
     * @return boolean representation of the players jail status
     * @author Gabriel Benni Kelley Evensen 101119814
     */
    public boolean handleInJail() {
        if (inJail && turnsInJail < 2) { //if the player has spent 1, or is on their second turn in jail (not counting the turn they were sent to jail), then skip their current turn
            turnsInJail++;
            this.currLocation.setIndex(9);
            return true;
        } else if (inJail && turnsInJail == 2) { //if the player is on their third turn in jail, then they may move
            this.inJail = false;
            this.turnsInJail = 0;
        }
        return false;
    }

    /**
     * getPlayerTotalAsset is used to determine the total amount of asset they own plus the balance is their wallet, this method is used to determine the winner
     * when the game ends
     *
     * @return int Total asset value + wallet balance
     * @author Yuguo Liu 101142730
     */
    public int getPlayerTotalAsset(){
        int TotalAsset = 0;
        for(PrivateProperty p: propertyList){
            if(p instanceof Business){
                TotalAsset += ((Business) p).getTotalAssetValue();
            }
            if(p instanceof Rail){
                TotalAsset += p.getPrice();
            }
        }
        TotalAsset += playerBalance;

        return TotalAsset;
    }

    /**
     * Getter for turn in jail
     * @return int
     */
    public int getTurnsInJail() {
        return turnsInJail;
    }

    /**
     * Increments jail time counter
     * until 3 rounds has been reached.
     * Returns true if jail sentence is over.
     * @return  boolean, is jail sentence over
     */
    public boolean serveJailTime() {
        turnsInJail++;
        if (turnsInJail >= 3) {
            turnsInJail = 0;
            inJail = false;
            return true;
        }
        return false;
    }

    /**
     * This method will represent the property of the Game.Player in String
     * @return String
     *
     * @author Gabriel Benni K. Evensen 101119814
     */
    public String propertiesToString(){
        StringBuilder sb = new StringBuilder();
        for (PrivateProperty pp : this.propertyList){
            sb.append("Name:- " + pp.getName() + " | Price:- $" + pp.getPrice());
            sb.append("<br>");
            if (pp instanceof Business) {
                sb.append("House: " ).append(((Business) pp).getNumHouse());
                sb.append(" | ");
                sb.append("Hotel: " ).append(((Business) pp).getNumHotel());
            }
            sb.append("<br>");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    /**
     * The method does the increment for player's turn
     *
     * @author Gabriel Benni K. Evensen 101119814
     */
    public void makeTurn(){
        this.turnsPlayed++;
    }

    /**
     * Getter for turn
     * @return int
     */
    public int getTurn() {
    return this.turnsPlayed;
    }

    public static class PlayerPropertyListModel extends DefaultListModel<PrivateProperty> {
        MonopolyModel model;

        public PlayerPropertyListModel(MonopolyModel model) {
            super();
            this.model = model;
            ArrayList<PrivateProperty> properties = this.model.getCurrentPlayer().getPropertyList();

            for (PrivateProperty p : properties) {
                addElement(p);
            }
        }

        public void removeProperty(int index) {
            removeElementAt(index);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return playerBalance == player.playerBalance && inJail == player.inJail && turnsInJail == player.turnsInJail && turnsPlayed == player.turnsPlayed && Objects.equals(name, player.name) && Objects.equals(currLocation, player.currLocation) && Objects.equals(playerWallet, player.playerWallet) && Objects.equals(propertyList, player.propertyList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, playerBalance, inJail, turnsInJail, turnsPlayed, currLocation, playerWallet, propertyList);
    }

    @Override
    public String toString() {
        // The format is name-playerBalance-inJail-turnsInJail-turnsPlayed-currLoc-propertyList

        StringBuilder sb = new StringBuilder();
        sb.append(name).append("-").
                append(playerBalance).append("-")
                .append(inJail).append("-")
                .append(turnsInJail).append("-")
                .append(turnsPlayed).append("-")
                .append(currLocation.getIndex()).append("-")
                .append(propertyListToString());

        return sb.toString();
    }

    public String propertyListToString() {

        // Format: squareIndex#squareIndex

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < propertyList.size(); i ++) {

            // last index, don't append the #
            if (i == propertyList.size() -1){
                sb.append(propertyList.get(i).getIndex());
            }

            else {
                sb.append(propertyList.get(i).getIndex()).append("#");
            }
        }

        return sb.toString();
    }

    public String toXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<Player>");
        sb.append(this);
        sb.append("</Player>");
        return sb.toString();
    }

    public static Player readFile(String aString, Board board) {
        // The format is name-playerBalance-inJail-turnsInJail-turnsPlayed-currLoc-
        // propertyList
        String[] list = aString.split("\\-");
        Square location = board.getSQUARE(Integer.parseInt(list[5]));
        Player newPlayer = new Player(list[0], Integer.parseInt(list[1]), Boolean.parseBoolean(list[2]),
                Integer.parseInt(list[3]), Integer.parseInt(list[4]),location);

        String[] propertyIndex = list[6].split("\\#");
        for (String index : propertyIndex) {
            int i = Integer.parseInt(index);
            Square property = board.getSQUARE(i);
            newPlayer.addPropertyList((PrivateProperty) property);
            ((PrivateProperty)property).setOwner(newPlayer);
        }
        //return new Player(list[0], list[1], list[2], list[3], list[4]);
        return newPlayer;
    }
}
