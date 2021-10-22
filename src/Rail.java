public class Rail extends PrivateProperty {

    public Rail(String name, int index, int price){
        super(name, index, price);
        removeOwner();
    }
}
