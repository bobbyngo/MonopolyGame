import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;


public class Player implements RoleAPI {
    /**
     * Author: Ngo Huu Gia Bao
     * 101163137
     */

    private String name;
    private int playerBalance;
    private boolean inJail;
    private int turnsInJail;
    private Square currLocation;
    private HashMap<Integer, Integer> playerWallet;
    private ArrayList<PrivateProperty> propertyList;

    /**
     * Constructor for Player class
     * @param name
     * @param currLocation
     */
    public Player(String name, Square currLocation) {
        this.name = name;

        this.turnsInJail = 0;
        this.playerWallet = new HashMap<>();

        this.inJail = false;
        this.currLocation = currLocation;
        this.propertyList = new ArrayList<>();
        this.playerBalance = 1230;
    }

    //                      ZAK BRO, I'M SORRY I DO NOT SURE IF THIS WORK

    /**
     * This method will take the roll's value and call the setCurrLocation of the player in the board
     * @param numRolled
     */
    /*
    public void moveTo(int numRolled) {
        int newIndex = (currLocation.getIndex() + numRolled) % 38;
        this.setCurrLocation(newIndex);
    }
     */

    /**
     * This method will set the new location of the player in the board
     * @param newIndex
     */
    /*
    private void setCurrLocation(int newIndex) {
        this.currLocation.setIndex(newIndex);
    }
     */


    /**
     * This method will let the player buy PrivateProperty. If the Player can buy the property,
     * it will call the addProperty method and set the owner of that Property
     * @param property
     */
    public void buyPrivateProperty(PrivateProperty property) {
        if (!property.isOwned()) {
            this.removeMoney(property.getPrice());
            this.addPropertyList(property);
            property.setOwner(this);
            System.out.println("You have bought the property");
        } else {
            System.out.println("This property is owned");
        }
    }

    /**
     * This method will add the Property to the Player's propertyList
     * @param property
     */
    private void addPropertyList(PrivateProperty property) {
        if (!propertyList.contains(property)) {
            this.propertyList.add(property);
        } else {
            System.out.println("You have this property already");
        }
    }


    /**
     * This method will call the isOwningASet of color in the ColorGroup Class
     * @return boolean
     */
    public boolean isOwningColorGroup() {
        ColorGroup colorGroup = new ColorGroup();
        return colorGroup.isOwningASet(this.propertyList);
    }

    public boolean isInJail() {
        return this.inJail;
    }

    /**
     * This method will add the money from the Player balance
     * @param amount
     */
    @Override
    public void addMoney(int amount) {
        playerBalance += amount;
    }

    /**
     * This method will remove the money from the Player balance
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
     * @return Square
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

    public boolean getInJail() {
        return this.inJail;
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
}
