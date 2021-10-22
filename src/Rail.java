public class Rail extends PrivateProperty implements PropertyAPI{

    public Rail(String name, int index, int price){
        super(name, index, price);
        removeOwner();
    }

    @Override
    public void collectMoney(int amount) {

    }
}
