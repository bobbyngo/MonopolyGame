package Game;

/**
 * Author: Patrick Liu
 * 101142730
 */

public class Rail extends PrivateProperty implements PropertyAPI, RentableAPI {

    /**
     * Constructor for Game.Rail class
     * @param name name of the Game.Rail
     * @param index index of the Game.Rail
     * @param price price of the Game.Rail
     */
    public Rail(String name, int index, int price){
        super(name, index, price);
        removeOwner();
    }

    /**
     * getOwnedRailNum() gets the number of Game.Rail SQUARE that this particular player owns
     * @return int
     */
    public int getOwnedRailNum(){
        int numRail = 0;
        if(this.isOwned()){
            for(PrivateProperty p: this.getOwner().getPropertyList()){
                if(p instanceof Rail){
                    numRail ++;
                }
            }
        }

        return numRail;
    }

    /**
     * sell returns the amount the bank will pay the player when they are bankrupt and has to sell this Game.Rail, it will also remove the owner
     * this Game.Business is ready to be bought by another player again
     * @return int
     *
     */
    public int sell(){
        //int sellPrice = (this.getPrice()/2);
        int sellPrice = getSalePrice();
        removeOwner();
        return sellPrice;
    }

    /**
     * get the rent amount of this property
     *
     * @return int
     */
    public int getRentAmount(){
        return (int)(this.getPrice() * 0.1 * this.getOwnedRailNum());
    }

    /**
     * @author Yuguo Liu 101142730
     *
     * Method to collect money from a player who lands on a square that is not theirs, and pays that rent to the player who owns the square
     * rent is 10% of the total property price (including houses & hotels)
     */
    public void collectMoney(Player player){
        player.removeMoney(this.getRentAmount());
        this.getOwner().addMoney(this.getRentAmount());
    }


    public int getSalePrice() {
        // i may or may not be breaking things...
        return getPrice()/2;
    }


    public String toXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<Rail>");
        //use PrivateProperty toString
        sb.append(this);
        sb.append("</Rail>\n");
        return sb.toString();
    }
}
