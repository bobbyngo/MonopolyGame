package Game;

import java.util.Objects;

/**
 * Author: Patrick Liu
 * 101142730
 */

public class Business extends PrivateProperty implements PropertyAPI, RentableAPI {

    private int numHouse;
    private int numHotel;

    /**
     * Constructor for Game.Business class
     * @param name name of the Game.Business
     * @param index index of the Game.Business
     * @param price price of the Game.Business
     */
    public Business(String name, int index, int price){
        super(name, index, price);
        numHouse = 0;
        numHotel = 0;
        removeOwner();
    }

    public Business(String name, int index, int price, boolean isOwned, int numHouse, int numHotel){
        super(name, index, price, isOwned);
        this.numHouse = numHouse;
        this.numHotel = numHotel;
    }

    /**
     * getNumHouses get the number of houses that has been bought on this Game.Business SQUARE
     * @return int
     */
    public int getNumHouse() {
        return numHouse;
    }

    /**
     * getNumHotel get the number of hotel that has been bought on this Game.Business SQUARE
     * @return int
     */
    public int getNumHotel() {
        return numHotel;
    }

    // Plz check if the player owns all 3 same colored SQUARES
    /**
     * buyHouse adds a house to this Game.Business SQUARE
     */
    public void buyHouse(){
        numHouse ++;
    }


    // Plz use getNumHouse() to check if the player has 4 houses already build on this SQUARE
    /**
     * buyHotel adds a hotel to this Game.Business SQUARE
     */
    public void buyHotel(){
        numHotel ++;
    }
    

    // Return the total value of the asset (SQUARE + all houses and hotel)
    /**
     * getTotalAssetValue returns the total value of this Game.Business,this includes the price of the square plus all the houses and hotel that were bought on it
     * @return int
     */
    public int getTotalAssetValue(){
        return (int)(this.getPrice() + numHouse * (100 + this.getPrice() * 0.1) + numHotel * (100 + this.getPrice() * 0.6));
    }

    // Only used when a player is out of money and is on the verge of Bankruptcy, the sell price is half of the total assets' value
    /**
     * sell returns the amount the bank will pay the player when they are bankrupt and has to sell this Game.Business, it will remove the owner and set all house number
     * and hotel number back to 0, this Game.Business is ready to be bought by another player
     * @return int
     *
     * Note: This method is not used in the current version of the game and should NOT be called
     */
    public int sell(){
        //int sellPrice =  (int)((this.getPrice() + numHouse * (100 + this.getPrice() * 0.1) + numHotel * (100 + this.getPrice() * 0.6))/2);
        int sellPrice = getSalePrice();
        removeOwner();
        numHouse = 0;
        numHotel = 0;
        return sellPrice;
    }

    /**
     * get the rent amount of this property
     *
     * @return int
     */
    public int getRentAmount(){
        return (int)(this.getTotalAssetValue()*0.1);
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
        //return getTotalAssetValue();
        int sellPrice =  (int)((this.getPrice() + numHouse * (100 + this.getPrice() * 0.1) + numHotel * (100 + this.getPrice() * 0.6))/2);
        return sellPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Business business = (Business) o;
        return numHouse == business.numHouse && numHotel == business.numHotel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numHouse, numHotel);
    }

    @Override
    public String toString() {

        //Format name-index-price-isOwned-numHouse-numHotel

        StringBuilder sb = new StringBuilder();
        sb.append(getName()).append("-").
                append(getIndex()).append("-")
                .append(getPrice()).append("-")
                .append(getIsOwned()).append("-")
                .append(numHouse).append("-")
                .append(numHotel);

        return sb.toString();
    }


    public String toXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<Business>");
        sb.append(this);
        sb.append("</Business>\n");
        return sb.toString();
    }


    public static Business readFile(String aString) {
        //Format name-index-price-isOwned-numHouse-numHotel
        String[] list = aString.split("\\-");

        return new Business(list[0], Integer.parseInt(list[1]), Integer.parseInt(list[2])
                , Boolean.parseBoolean(list[3]), Integer.parseInt(list[4]), Integer.parseInt(list[5]));
    }

}
