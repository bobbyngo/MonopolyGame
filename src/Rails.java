public class Rails extends Property{

    public Rails(String name, int index, String color, int price){
        super(name, index, color, price);
        removeOwner();
    }
}
