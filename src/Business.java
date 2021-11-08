/**
 * Author: Patrick Liu
 * 101142730
 */

public class Business extends PrivateProperty implements PropertyAPI{

    private String color;
    private int numHouse;
    private int numHotel;

    /**
     * Constructor for Business class
     * @param name name of the Business
     * @param index index of the Business
     * @param color color code of the housing line that this Business belongs to
     * @param price price of the Business
     */
    public Business(String name, int index, String color, int price){
        super(name, index, price);
        this.color = color;
        numHouse = 0;
        numHotel = 0;
        removeOwner();
    }

    /**
     * getColor get the color code of the housing line that this Business belongs to
     * @return String
     */
    public String getColor() {
        return color;
    }

    /**
     * getNumHouses get the number of houses that has been bought on this Business SQUARE
     * @return int
     */
    public int getNumHouse() {
        return numHouse;
    }

    /**
     * getNumHotel get the number of hotel that has been bought on this Business SQUARE
     * @return int
     */
    public int getNumHotel() {
        return numHotel;
    }

    // Plz check if the player owns all 3 same colored SQUARES
    /**
     * buyHouse adds a house to this Business SQUARE
     */
    public void buyHouse(){
        numHouse ++;
    }

    /**
     * @author Gabriel Benni Kelley Evensen 101119814
     * Method to remove houses from Business square (when replacing for hotels only)
     */
    public void removeHouses(){
        numHouse = 0;
    }

    // Plz use getNumHouse() to check if the player has 4 houses already build on this SQUARE
    /**
     * buyHotel adds a hotel to this Business SQUARE
     */
    public void buyHotel(){
        numHotel ++;
    }

    // Return the total value of the asset (SQUARE + all houses and hotel)
    /**
     * getTotalAssetValue returns the total value of this Business,this includes the price of the square plus all the houses and hotel that were bought on it
     * @return int
     */
    public int getTotalAssetValue(){
        return (int)(this.getPrice() + numHouse * (100 + this.getPrice() * 0.1) + numHotel * (100 + this.getPrice() * 0.6));
    }

    // Only used when a player is out of money and is on the verge of Bankruptcy, the sell price is half of the total assets' value
    /**
     * sell returns the amount the bank will pay the player when they are bankrupt and has to sell this Business, it will remove the owner and set all house number
     * and hotel number back to 0, this Business is ready to be bought by another player
     * @return int
     *
     * Note: This method is not used in the current version of the game and should NOT be called
     */
    public int sell(){
        int sellPrice =  (int)((this.getPrice() + numHouse * (100 + this.getPrice() * 0.1) + numHotel * (100 + this.getPrice() * 0.6))/2);
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

    /**
     * @author Gabriel Benni Kelley Evensen 101119814
     * @author Yuguo Liu 101142730
     *
     * Method to collect money from a player who lands on a square that is not theirs, and pays that rent to the player who owns the square
     * rent is 10% of the total property price (including houses & hotels)
     */
    /*
    @Override
    public void collectMoney() {
        this.getPlayersCurrentlyOn().get(0).removeMoney(this.getRentAmount());            //Removes the rent price from the player who most recently stepped on property (index 0 is most recent b/c FIFO)
        this.getOwner().addMoney(this.getRentAmount());                                   //Gives rent price to owner of property
    }
    */

    @Override
    public String toString() {
        return "Business{" +
                "color='" + color + '\'' +
                ", numHouse=" + numHouse +
                ", numHotel=" + numHotel +
                "} " + super.toString();
    }
}
