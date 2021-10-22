public class Business extends Property{

    public Business(String name, int index, String color, int price){
        super(name, index, color, price);
        removeOwner();
    }
}
