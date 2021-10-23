public class Rail extends PrivateProperty implements PropertyAPI{

    public Rail(String name, int index, int price){
        super(name, index, price);
        removeOwner();
    }

    public int sell(){
        int sellPrice = (this.getPrice()/2);
        removeOwner();
        return sellPrice;
    }

    @Override
    public void collectMoney(int amount) {

    }
}
