/**
 * Author: Patrick Liu
 * 101142730
 */

public class PrivateProperty extends Square{
    private int price;
    private Player owner;
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
        owner = null;
    }

    /**
     * addOwner is used to add owner and set the Private Property status to owned
     */
    public void setOwner(Player player){
        owner = player;
        isOwned = true;
    }

    /**
     * removeOwner is used to remove owner and set the Private Property's status back to available for purchase
     */
    public void removeOwner(){
        owner = null;
        isOwned = false;
    }

    /**
     * getOwner is used to get then owner of the Private Property
     * @return Player
     */
    public Player getOwner() {
        return owner;
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
    public boolean isOwned(){
        return isOwned;
    }

    @Override
    public String toString() {
        /*
        return "PrivateProperty{" +
                "price=" + price +
                ", owner=" + ((owner != null) ? owner.getName() : "NULL") +
                ", isOwned=" + isOwned +
                "} " + super.toString();

         */

        return this.getName() +
                " - Sell Price: $" +
                (int)(this.getPrice()/2);
    }
}
