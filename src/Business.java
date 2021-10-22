public class Business extends PrivateProperty {
    private String color;
    public Business(String name, int index, String color, int price){
        super(name, index, price);
        this.color = color;
        removeOwner();
    }

    public String getColor() {
        return color;
    }
}
