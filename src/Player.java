import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player implements RoleAPI {


    //For buying property: Check if the class is Rail by: .instanceof(Rail)
    // If yes, we do not need to check the full set of color to buy rail
    // I agree, if .instanceof(Rail) works as intended then we do not need to use color code "white" for rail

        private String name;
        private HashMap<Integer, Integer> playerWallet;
        private boolean inJail;
        private int turnsInJail;
        private Square currLocation;
        private ArrayList<PrivateProperty> propertyList;

        //For buying property: Check if the class is Rail by: .instanceof(Rail)
        // If yes, we do not need to check the full set of color to buy rail

        public Player(String name, Square currLocation){
            this.name = name;
            this.turnsInJail = 0;
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
            return this.inJail;
        }

        @Override
        public void addMoney(int moneyValue, int amount) {
            if (this.playerWallet.containsKey(moneyValue)) {
                int previousAmount = playerWallet.get(moneyValue);
                playerWallet.put(moneyValue, previousAmount + amount);
            }
        }

        @Override
        public void removeMoney(int moneyValue, int amount) {
            if (this.playerWallet.containsKey(moneyValue)) {
                int previousAmount = playerWallet.get(moneyValue);
                if (previousAmount < amount) {
                    System.err.println(this.name + " has insufficient funds.");
                    return;
                }
                playerWallet.put(moneyValue, previousAmount - amount);
            }
        }

    @Override
    public String walletToString(){
        String s = this.name + " has:- \n";
        for (Map.Entry<Integer, Integer> entry : this.playerWallet.entrySet()){
            s.concat(entry.getValue() + "x $" + entry.getKey() + "\n");
        }
        return s;
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

        public boolean getInJail(){ return this.inJail; }

        public void setJail(boolean jailStatus){ this.inJail = jailStatus; }

    /**
     * Method handler to check if a player is to remain in jail for this turn (returns false); or if a player should be allowed to move this turn (returns true)
     * @return
     */
    public boolean handleInJail(){
            if (inJail && turnsInJail < 2) { //if the player has spent 1, or is on their second turn in jail, then skip their current turn
                turnsInJail++;
                return false;
            }
            else if(inJail && turnsInJail == 2){ //if the player is on their third turn in jail, then they may move
                this.inJail = false;
                turnsInJail = 0;
            }
            return true;
        }

}
