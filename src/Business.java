public class Business extends PrivateProperty implements PropertyAPI{

    private String color;
    private int numHouse;
    private int numHotel;

    public Business(String name, int index, String color, int price){
        super(name, index, price);
        this.color = color;
        numHouse = 0;
        numHotel = 0;
        removeOwner();
    }

    public String getColor() {
        return color;
    }

    public int getNumHouse() {
        return numHouse;
    }

    public int getNumHotel() {
        return numHotel;
    }

    // Plz check if the player owns all 3 same colored SQUARES
    public void buildHouse(){
        numHouse ++;
    }

    // Plz use getNumHouse() to check if the player has 4 houses already build on this SQUARE
    public void buildHotel(){
        numHotel ++;
    }

    // Only used when a player is out of money and is on the verge of Bankruptcy, the sell price is half of the total assets' value
    public int sell(){
        int sellPrice =  (int)((this.getPrice() + numHouse * (100 + this.getPrice() * 0.1) + numHotel * (100 + this.getPrice() * 0.6))/2);
        removeOwner();
        numHouse = 0;
        numHotel = 0;
        return sellPrice;
    }

    @Override
    public void collectMoney(int amount) {

    }
}
