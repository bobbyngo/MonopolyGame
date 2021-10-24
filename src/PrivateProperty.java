/**
 * Author: Patrick Liu
 * 101142730
 */

public class PrivateProperty extends Square{
    private int price;
    private boolean isOwned;

    /**
     * Constructor for PrivateProperty class
     * @param name name of the Private Property
     * @param index index of the Private Property
     * @param price price of the Private Property
     */
    public PrivateProperty(String name, int index, int price){
        super(name, index);
        isOwned = false;
        this.price = price;
    }

    /**
     * addOwner is used to set the Private Property status to owned
     */
    public void addOwner(){
        isOwned = true;
    }

    /**
     * removeOwner is used to set the Private Property status to available for purchase
     * Note: this method is not used in the current version of the game and should NOT be called
     */
    public void removeOwner(){
        isOwned = false;
    }

    /**
     * getPrice is get the listed price of the Private Property
     * @return int
     */
    public int getPrice() {
        return price;
    }

    /**
     * getIsOwned is used to determine whether a property has an owner or not
     * @return boolean
     */
    public boolean getIsOwned(){
        return isOwned;
    }
}
