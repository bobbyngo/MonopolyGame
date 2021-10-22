public class PrivateProperty extends Square{
    private int price;
    private boolean isOwned;

    public PrivateProperty(String name, int index, int price){
        super(name, index);
        isOwned = false;
        this.price = price;
    }

    public void addOwner(){
        isOwned = true;
    }

    public void removeOwner(){
        isOwned = false;
    }

    public int getPrice() {
        return price;
    }

    public boolean getIsOwned(){
        return isOwned;
    }
}
