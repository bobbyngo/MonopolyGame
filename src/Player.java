import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;


public class Player implements RoleAPI {
    /**
     * Author: Ngo Huu Gia Bao
     * 101163137
     */

    //For buying property: Check if the class is Rail by: .instanceof(Rail)
    // If yes, we do not need to check the full set of color to buy rail
    // I agree, if .instanceof(Rail) works as intended then we do not need to use color code "white" for rail

    private String name;
    private int playerBalance;
    private boolean inJail;
    private int turnsInJail;
    private Square currLocation;
    private HashMap<Integer, Integer>playerWallet;
    private ArrayList<PrivateProperty> propertyList;

    //For buying property: Check if the class is Rail by: .instanceof(Rail)
    // If yes, we do not need to check the full set of color to buy rail

    public Player(String name, Square currLocation){
        this.name = name;

        this.turnsInJail = 0;
        this.playerWallet = new HashMap<>();

        this.inJail = false;
        this.currLocation = currLocation;
        this.propertyList = new ArrayList<>();
        this.playerBalance = 1230;
    }

    public void moveTo(int numRolled) {
        int newIndex = (currLocation.getIndex() + numRolled) % 38;
        this.setCurrLocation(newIndex);
    }

    private void setCurrLocation(int newIndex) {
        this.currLocation.setIndex(newIndex);
    }

    public void buyPrivateProperty(PrivateProperty property) {
        if (!property.getIsOwned()) {
            this.removeMoney(property.getPrice());
            this.addPropertyList(property);
            property.addOwner();
        }else {
            System.out.println("This property is owned");
        }
    }

    public void sellPrivateProperty(PrivateProperty property) {
        if(this.propertyList.size() > 0 && this.propertyList.contains(property)) {
            this.removePropertyList(property);
        }else {
            System.out.println("You do not have this property");
        }
    }

    private void addPropertyList(PrivateProperty property) {
        if (!propertyList.contains(property)) {
            this.propertyList.add(property);
        }else {
            System.out.println("You have this property already");
        }
    }

    private void removePropertyList(PrivateProperty property) {
        this.propertyList.remove(property);
    }

    public boolean isOwningColorGroup(){
        return false;
    }

    public boolean isInJail(){
        return this.inJail;
    }

    @Override
    public void addMoney(int amount) {
        playerBalance += amount;
    }

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
     * @author Gabriel Benni Kelley Evensen 101119814
     * @return String
     */
    @Override
    public String walletToString(){
        String s = this.name + " has:- \n";
        for (Map.Entry<Integer, Integer> entry : this.playerWallet.entrySet()){
            s.concat(entry.getValue() + "x $" + entry.getKey() + "\n");

        }
        return s;
    }

    public boolean isBankrupt() {
        return false;
    }

    public String getName() {
        return name;
    }

    public int getPlayerBalance() {
        return playerBalance;
    }

    public Square getCurrLocation() {
        return currLocation;
    }

    public ArrayList<PrivateProperty> getPropertyList() {
        return propertyList;
    }

    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }


    public void setCurrLocation(Square currLocation) {
        this.currLocation = currLocation;
    }

    public boolean getInJail(){ return this.inJail; }

    public void setJail(boolean jailStatus){ this.inJail = jailStatus; }

    /**
     * Method handler to check if a player is to remain in jail for this turn (returns false); or if a player should be allowed to move this turn (returns true)
     * @author Gabriel Benni Kelley Evensen
     * @return
     */
    public boolean handleInJail(){
        if (inJail && turnsInJail < 2) { //if the player has spent 1, or is on their second turn in jail, then skip their current turn
            turnsInJail++;
            return false;
        }
        else if(inJail && turnsInJail == 2){ //if the player is on their third turn in jail, then they may move
            this.inJail = false;
            turnsInJail = 0;
        }
        return true;
    }

}
