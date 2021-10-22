import java.util.ArrayList;
import java.util.HashMap;

public class Player implements RoleAPI {


    //For buying property: Check if the class is Rail by: .instanceof(Rail)
    // If yes, we do not need to check the full set of color to buy rail
    // I agree, if .instanceof(Rail) works as intended then we do not need to use color code "white" for rail

        private String name;
        private HashMap<Integer, Integer> playerWallet;
        private boolean inJail;
        private Square currLocation;
        private ArrayList<PrivateProperty> propertyList;

        //For buying property: Check if the class is Rail by: .instanceof(Rail)
        // If yes, we do not need to check the full set of color to buy rail

        public Player(String name, Square currLocation){
            this.name = name;
            this.playerWallet = new HashMap<>();
            this.inJail = false;
            this.currLocation = currLocation;
            this.propertyList = new ArrayList<>();

            //Initialize Player's wallet
            playerWallet.put(1, 10);
            playerWallet.put(5, 10);
            playerWallet.put(10, 10);
            playerWallet.put(50, 10);
            playerWallet.put(100, 10);
            playerWallet.put(500, 10);
        }


        public void buyPrivateProperty() {

        }

        public boolean isOwningColorGroup(){
            return false;
        }

        public boolean isInJail(){
            return false;
        }

        @Override
        public void addMoney(int moneyValue, int amount) {

        }

        @Override
        public void removeMoney(int moneyValue, int amount) {

        }

        public boolean isBankrupt() {
            return false;
        }

        public String getName() {
            return name;
        }

        public Square getCurrLocation() {
            return currLocation;
        }

        public ArrayList<PrivateProperty> getPropertyList() {
            return propertyList;
        }

        public HashMap<Integer, Integer> getPlayerWallet() {
            return playerWallet;
        }

        public void setInJail(boolean inJail) {
            this.inJail = inJail;
        }

        public void setCurrLocation(Square currLocation) {
            this.currLocation = currLocation;
        }

}
