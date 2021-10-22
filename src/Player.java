import java.util.ArrayList;

public class Player implements RoleAPI {
    /**
     * Author: Ngo Huu Gia Bao
     * 101163137
     */

    //For buying property: Check if the class is Rail by: .instanceof(Rail)
    // If yes, we do not need to check the full set of color to buy rail
    // I agree, if .instanceof(Rail) works as intended then we do not need to use color code "white" for rail

        private String name;
        private int playerBalance;
        private boolean inJail;
        private Square currLocation;
        private ArrayList<PrivateProperty> propertyList;

        //For buying property: Check if the class is Rail by: .instanceof(Rail)
        // If yes, we do not need to check the full set of color to buy rail

        public Player(String name, Square currLocation){
            this.name = name;
            this.inJail = false;
            this.currLocation = currLocation;
            this.propertyList = new ArrayList<>();
            this.playerBalance = 1230;
        }

        public void moveTo(int numRolled) {
            int newIndex = (currLocation.getIndex() + numRolled) % 38;
            this.setCurrLocation(newIndex);
        }

        private void setCurrLocation(int newIndex) {
            this.currLocation.setIndex(newIndex);
        }

        public void buyPrivateProperty(PrivateProperty property) {
            if (!property.getIsOwned()) {
                this.removeMoney(property.getPrice());
                this.addPropertyList(property);
                property.addOwner();
            }else {
                System.out.println("This property is owned");
            }
        }

        public void sellPrivateProperty(PrivateProperty property) {
            if(this.propertyList.size() > 0 && this.propertyList.contains(property)) {
                this.removePropertyList(property);
            }else {
                System.out.println("You do not have this property");
            }
        }

        private void addPropertyList(PrivateProperty property) {
            if (!propertyList.contains(property)) {
                this.propertyList.add(property);
            }else {
                System.out.println("You have this property already");
            }
        }

        private void removePropertyList(PrivateProperty property) {
            this.propertyList.remove(property);
        }

        public boolean isOwningColorGroup(){
            return false;
        }

        public boolean isInJail(){
            return false;
        }

        @Override
        public void addMoney(int amount) {
            playerBalance += amount;
        }

        @Override
        public void removeMoney(int amount) {
            if (playerBalance > amount) {
                playerBalance -= amount;
            }else {
                System.out.println("Your balance is not enough");
            }
        }

        public boolean isBankrupt() {
            return false;
        }

        public String getName() {
            return name;
        }

        public int getPlayerBalance() {
            return playerBalance;
        }

        public Square getCurrLocation() {
            return currLocation;
        }

        public ArrayList<PrivateProperty> getPropertyList() {
            return propertyList;
        }

        public void setInJail(boolean inJail) {
            this.inJail = inJail;
        }

}
