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
     * Note: This method is not used in the current version of the game and should NOT be called
     */
    public int sell(){
        int sellPrice = (this.getPrice()/2);
        removeOwner();
        return sellPrice;
    }

    @Override
    public void collectMoney(int amount) {

    }
}
