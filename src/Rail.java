/**
 * Author: Patrick Liu
 * 101142730
 */

public class Rail extends PrivateProperty implements PropertyAPI{

    /**
     * Constructor for Rail class
     * @param name name of the Rail
     * @param index index of the Rail
     * @param price price of the Rail
     */
    public Rail(String name, int index, int price){
        super(name, index, price);
        removeOwner();
    }

    /**
     * getOwnedRailNum() gets the number of Rail SQUARE that this particular player owns
     * @return int
     */
    public int getOwnedRailNum(){
        int numRail = 0;
        for(PrivateProperty p: this.getOwner().getPropertyList()){
            if(p instanceof Rail){
                numRail ++;
            }
        }

        return numRail;
    }

    /**
     * sell returns the amount the bank will pay the player when they are bankrupt and has to sell this Rail, it will also remove the owner
     * this Business is ready to be bought by another player again
     * @return int
     *
     */
    public int sell(){
        int sellPrice = (this.getPrice()/2);
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

    /**
     * @author Gabriel Benni Kelley Evensen 101119814
     * @author Yuguo Liu 101142730
     *
     * Method to collect money from a player who lands on a square that is not theirs, and pays that rent to the player who owns the square
     * rent is 10% of the total property price
     */
    /*
    @Override
    public void collectMoney() {
        this.getPlayersCurrentlyOn().get(0).removeMoney(this.getRentAmount());     //Removes the rent price from the player who most recently stepped on property (index 0 is most recent b/c FIFO)
        this.getOwner().addMoney(this.getRentAmount());                            //Gives rent price to owner of property
    }
     */
}
