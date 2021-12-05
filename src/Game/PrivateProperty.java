package Game;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Author: Patrick Liu
 * 101142730
 */

public class PrivateProperty extends Square {
    private int price;
    private Player owner;
    private boolean isOwned;
    //private ArrayList<PrivateProperty> propertyList;

    /**
     * Constructor for Game.PrivateProperty class
     * @param name name of the Private Property
     * @param index index of the Private Property
     * @param price price of the Private Property
     */
    public PrivateProperty(String name, int index, int price){
        super(name, index);
        isOwned = false;
        this.price = price;
        owner = null;
        //this.propertyList = new ArrayList<>();
    }

    public PrivateProperty(String name, int index, int price, boolean isOwned){
        super(name, index);
        this.price = price;
        this.isOwned = isOwned;
        this.owner = null;
        //this.propertyList = new ArrayList<>();
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
     * @return Game.Player
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

    public boolean getIsOwned() {
        return this.isOwned;
    }

    /**
     * getIsOwned is used to determine whether a property has an owner or not
     * @return boolean
     */
    public boolean isOwned(){
        return isOwned;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivateProperty property = (PrivateProperty) o;
        return price == property.price && isOwned == property.isOwned && Objects.equals(owner, property.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, owner, isOwned);
    }

    public void addPrivateProperty(PrivateProperty pp) {
        //propertyList.add(pp);
    }


    // Do we need toXML for PP?
//    public String toXML() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("<PrivateProperty>");
//        for (){
//
//        }
//        sb.append("</PrivateProperty>");
//        return sb.toString();
//    }

    //TODO: Careful the player attribute
    @Override
    public String toString() {

        //Format name-index-price-isOwned-owner
        // owner will have problem maybe? I set owner name

        StringBuilder sb = new StringBuilder();

        sb.append(getName()).append("-")
                .append(getIndex()).append("-")
                .append(getPrice()).append("-")
                .append(isOwned).append("-")
                .append(owner.getName());

        return sb.toString();
    }

    public PrivateProperty readFile(String aString) {
        //Format name-index-price-isOwned
        String[] list = aString.split("\\-");

        return new PrivateProperty(list[0], Integer.parseInt(list[1]), Integer.parseInt(list[2])
                , Boolean.parseBoolean(list[3]));
    }
}
