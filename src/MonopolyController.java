import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author Gabriel Benni Kelley Evensen 101119814
 *
 * The controller for the game of monopoly; handles the players, board, banks, DIE, and the current player
 */
public class MonopolyController implements ActionListener {
    private final int GO_REWARD = 200;

    private ArrayList<Player> players;
    private Board board;
    private Bank bank;
    private Dice die;
    private Player currentPlayer;
    private int consecutiveDoubles;

    // MVC Example
    private MonopolyGUIView view;

    int[] roll;
    private boolean diceRolled = false;
    private boolean feePaid = true;

    /**
     * MonopolyController constructor
     *
     * @param players, an ArrayList of Player's that are in the game
     */
    public MonopolyController(ArrayList<Player> players, MonopolyGUIView view) {

        this.players = new ArrayList<>(players);
        this.board = new Board();
        this.bank = new Bank();
        this.die = new Dice();
        //this.currentPlayer = players.get(players.size()-1);
        this.currentPlayer = players.get(0);

        // MVC example
        this.view = view;

        for(Player p: this.players){
            p.setCurrLocation(board.getSQUARE(0));
        }
    }

    /**
     * Method to sell the current rail property and returns half the worth of the property to the seller.
     * Method overloading used as both Rail and Business are polymorphs when selling (b/c business may have houses/hotels)
     *
     * @param property, the rail property being sold
     */
    public void sellProperty(Rail property) {
        this.currentPlayer.addMoney(property.sell());
    }

    /**
     * Method to sell the current business property and returns half the worth of the property to the seller.
     * Method overloading used as both Rail and Business are polymorphs when selling (b/c business may have houses/hotels)
     *
     * @param property, the business property being sold
     */
    public void sellProperty(Business property) {
        this.currentPlayer.addMoney(property.sell());
    }

    /**
     * Method to purchase the current PrivateProperty, or a house/hotel for the current Business property (cast from the PrivateProperty).
     *
     * If the currentPlayer already owns a full colour set of Business Properties, then they automatically buy one house, iff they already have ...
     * ...four houses on one square, those houses get replaced with one hotel (one hotel is worth more than four houses)
     *
     * @param property, the privateproperty property being sold
     */
    public void purchaseProperty(PrivateProperty property) {
        if (property instanceof Rail) {
            this.currentPlayer.buyPrivateProperty(property);                                            //This adds property and removes money for purchase
            this.bank.addMoney(property.getPrice());                                                    //This adds the money from the purchase to the bank
        }
        else if (property instanceof Business && !this.currentPlayer.isOwningColorGroup()) {     //Else if the property is Business Property and the currentPlayer does not own a full set; they may buy it
            this.currentPlayer.buyPrivateProperty(property);                                            //This adds property and removes money for purchase
            this.bank.addMoney(property.getPrice());
        }else if (property instanceof Business && this.currentPlayer.isOwningColorGroup()) {            //The property type must be Business; if the currentPlayer owns the full colour set, then:-
            Business businessProperty = (Business) property;                                            //Cast privateproperty type to Business for manipulation

            if (businessProperty.getNumHouse() < 4) {                                                   //If the Business Property has less than the maximum allowed houses, 5
                businessProperty.buyHouse();                                                            //Increments house count by one
                this.currentPlayer.removeMoney((int) (100 + businessProperty.getPrice() * 0.1));        //Removes price of one house from currentPlayers purse
                this.bank.addMoney((int) (100 + businessProperty.getPrice() * 0.1));
            } else if (businessProperty.getNumHouse() == 4) {                                           //Else if the Business Property has four houses, replace those four with a hotel, worth more than four houses
                businessProperty.removeHouses();                                                        //Removes all houses from the current property
                businessProperty.buyHotel();                                                            //Puts one hotel on the property
                this.currentPlayer.removeMoney((int) (100 + businessProperty.getPrice() * 0.6));        //Removes price of one hotel from currentPlayers purse
                this.bank.addMoney((int) (100 + businessProperty.getPrice() * 0.6));                    //This adds the money from the purchase to the bank
            }

        } else {
            System.err.println("You are unable to buy anything on this square");
        }
        //For buying property: Check if the class is Rail by: .instanceof(Rail)
        // If yes, we do not need to check the full set of color to buy rail
        // I agree, if .instanceof(Rail) works as intended then we do not need to use color code "white" for rail
    }

    /**
     * Accessor method for the players
     * @return the ArrayList<Player>
     */
    public ArrayList<Player> getPlayers(){ return this.players; }

    /**
     * Mutator method for
     * @param players; the ArrayList<Player> to be assigned
     */
    public void setPlayers(ArrayList<Player> players) { this.players = players; }

    /**
     * Accessor method for the board
     * @return the board
     */
    public Board getBoard(){ return this.board; }

    /**
     * Mutator method for
     * @param board; the board to be assigned
     */
    public void setBoard(Board board){ this.board = board; }

    /**
     * Accessor method for the bank
     * @return the bank
     */
    public Bank getBank(){ return this.bank; }

    public void showStats(){

    }
    /**
     * Mutator method for
     * @param bank; the bank to be assigned
     */
    public void setBank(Bank bank){ this.bank = bank; }

    /**
     * Accessor method for Die
     * @return the set of die
     */
    public Dice getDie(){ return this.die; }

    /**
     * Mutator method for
     * @param die; the set of die to be assigned
     */
    public void setDie(Dice die) { this.die = die; }

    /**
     * Accessor method for currentPlayer
     * @return the current player
     */
    public Player getCurrentPlayer(){ return this.currentPlayer; }

    /**
     * Mutator method for currentPlayer
     * @param currentPlayer; the next currentPlayer
     */
    public void setCurrentPlayer(Player currentPlayer){ this.currentPlayer = currentPlayer; }

    /**
     * @author Zakaria Ismail 101143497
     * Gets the next Player in turn circular queue
     * @return  Player, currentPlayer
     *
     */
    public Player getNextPlayer() {
        consecutiveDoubles = 0; // reset doubles counter
        int next_index = (players.indexOf(currentPlayer) + 1) % players.size();
        currentPlayer = players.get(next_index);
        return currentPlayer;
    }

    public int[] rollDie() {
        die.roll();
        if (die.isDouble()) {
            consecutiveDoubles++;
        }
        return die.getDice();
    }

    /**
     * Determines if currentPlayer is speeding
     * @return  boolean, currentPlayer is speeding
     * @author  Zakaria Ismail, 101143497
     */
    public boolean isSpeeding() {
        return consecutiveDoubles >= 3;
    }

    /**
     * Sets currentPlayer as being in Jail
     * @author  Zakaria Ismail, 101143497
     */
    public void sendCurrentPlayerToJail() {
        currentPlayer.getInJail();
        currentPlayer.setCurrLocation(board.getJail());
    }

    /**
     * Moves currentPlayer forward the number
     * rolled by dice. Returns true if passed
     * go.
     * @return  boolean, currentPlayer passed go
     * @author  Zakaria Ismail, 101143497
     */
    public boolean moveCurrentPlayer() {
        int start_index, end_index;
        start_index = currentPlayer.getCurrLocation().getIndex();
        //currentPlayer.moveTo(die.getTotal());
        end_index = (start_index + die.getTotal()) % board.getLENGTH();
        currentPlayer.setCurrLocation(board.getSQUARE(end_index));

        //end_index = currentPlayer.getCurrLocation().getIndex();

        if (end_index < start_index) {
            bank.removeMoney(GO_REWARD);
            currentPlayer.addMoney(GO_REWARD);
            return true;
        }
        return false;
    }


    /**
     * Checks if currentPlayer is on
     * 'Go To Jail' square
     * @return  boolean, currentPlayer is in jail
     * @author  Zakaria Ismail, 101143497
     */
    public boolean currentPlayerIsOnGoToJail() {
        //return currentPlayer.getCurrLocation().getName().equals("Go To Jail");
        return currentPlayer.getCurrLocation() == board.getGoToJail();
    }

    /**
     * Get Go reward.
     * @return  int, GO reward
     * @author  Zakaria Ismail, 101143497
     */
    public int getGO_REWARD() {
        return GO_REWARD;
    }

    /**
     * Checks if currentPlayer is on
     * 'Free Parking' square
     * @return  boolean, currentPlayer is in Free Parking
     * @author  Zakaria Ismail, 101143497
     */
    public boolean currentPlayerIsOnParking() {
        return currentPlayer.getCurrLocation() == board.getFreeParking();
    }

    /**
     * Checks if currentPlayer is on
     * owned property.
     * @return  boolean, current location is owned
     * @author  Zakaria Ismail, 101143497
     */
    public boolean currentPlayerIsOnOwnedProperty() {
        boolean isOwned;

        Square currLocation = currentPlayer.getCurrLocation();
        if (currLocation instanceof PrivateProperty) {
            // Private Property
            isOwned = ((PrivateProperty) currLocation).isOwned();
        } else if (currLocation instanceof BankProperty) {
            // Bank property
            isOwned = true;
        } else {
            // "Blanks": Go, (visiting) jail, Free parking
            isOwned = false;
        }
        return isOwned;
    }

    /**
     * Checks that currentPlayer is on
     * FreeParking, Go, or Jail
     * @return  boolean, currentPlayer is on FreeParking/Go/Jail
     * @author  Zakaria Ismail, 101143497
     */
    public boolean currentPlayerIsOnBlank() {
        Square currLocation = currentPlayer.getCurrLocation();
        for (Square sq : new Square[]{board.getFreeParking(), board.getGo(), board.getJail()}) {
            if (currLocation == sq) {
                return true;
            }
        }
        return false;
    }

    /**
     * determineWinner gets the player who has the greatest amount of total asset, only used when the game ends
     *
     * @return Player
     * @author Yuguo Liu 101142730
     */
    public Player determineWinner() {
        int winnerIndex = 0;
        int winningAmount = 0;
        for(Player p: players){
            if(!p.isBankrupt() && winningAmount < p.getPlayerTotalAsset()){
                winningAmount = p.getPlayerTotalAsset();
                winnerIndex = players.indexOf(p);
            }
        }

        return players.get(winnerIndex);
    }

    /**
     * This method will let the player sells the Property
     * @param index
     */
    public void sellProperty(int index) {
        PrivateProperty property = currentPlayer.getPropertyList().get(index);
        int cashEarned = (int)(property.getPrice() * 0.5);
        currentPlayer.addMoney(cashEarned);    // will prob need fix: give half
        bank.removeMoney(cashEarned);
        currentPlayer.removeProperty(property);
    }

    /**
     * payFee is used for the current player to pay rent and tax
     *
     * @return int which indicates whether the transaction is successful or not, 1 means paid, 0 means not paid
     * @author Yuguo Liu 101142730
     */
    public int payFee(){
        Square location = currentPlayer.getCurrLocation();

        if(location instanceof Business){
            if(currentPlayer.getPlayerBalance() < ((Business)currentPlayer.getCurrLocation()).getPrice()){
                return 0;
            }else{
                ((Business) location).collectMoney(currentPlayer);
                return 1;
            }
        }else if(location instanceof Rail){
            if(currentPlayer.getPlayerBalance() < ((Rail)currentPlayer.getCurrLocation()).getPrice()){
                return 0;
            }else{
                ((Rail) location).collectMoney(currentPlayer);
                return 1;
            }
        }else if(location instanceof BankProperty){
            if(currentPlayer.getPlayerBalance() < ((BankProperty)currentPlayer.getCurrLocation()).getTaxValue()){
                return 0;
            }else {
                ((BankProperty) location).collectMoney(currentPlayer);
                getBank().addMoney(((BankProperty) location).getTaxValue());
                return 1;
            }
        }
        return 0;
    }

    /**
     *
     * This method will check if the player is bankrupt or not
     * @return boolean
     */
    public boolean isGameEnded() {
        int totalAsset = currentPlayer.getPlayerTotalAsset();
        Square currentLocation = currentPlayer.getCurrLocation();
        int fee = 0;

        if (currentLocation instanceof BankProperty) {
            fee = ((BankProperty) currentLocation).getTaxValue();
        }
        else if (currentLocation instanceof PrivateProperty) {
            fee = ((PrivateProperty) currentLocation).getPrice();
        }

        if (totalAsset < fee) {
            return true;
        }

        return false;
    }

    // MVC Example
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.getBuyBtn()){
            handleBuyBtn();
        }
        else if (e.getSource() == view.getEndTurnBtn()) {
            handleEndTurnBtn();
        }
        else if(e.getSource() == view.getPayTaxBtn()){
            handlePayTaxBtn();
        }
        //Small change
        else if (e.getSource() == view.getRollBtn()) {
            try {
                handleRollDiceBtn();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void handleBuyBtn(){
        if (getCurrentPlayer().getCurrLocation() instanceof PrivateProperty) {
            if (!((PrivateProperty) getCurrentPlayer().getCurrLocation()).isOwned()) {
                purchaseProperty((PrivateProperty)getCurrentPlayer().getCurrLocation());
                view.handleUpdateView(1, getCurrentPlayer());
            } else {
                view.handleUpdateView(2, getCurrentPlayer());
            }
        } else {
            view.handleUpdateView(3, getCurrentPlayer());
        }
    }

    // This method should be private, change back to private when handleRollDiceBtn() is refactored into the controller class
    public void handleEndTurnBtn(){
        if(feePaid && diceRolled) {
            if(!getDie().isDouble()){
                // not double
                getNextPlayer();
            } else {
                // is double
                if (getCurrentPlayer().isInJail()) {
                    // is in jail
                    getNextPlayer();
                }
            }

            //Player p = controller.getNextPlayer();
            Player p = getCurrentPlayer();

            view.handleUpdateView(4, p);
            if (p.isInJail()) {
                boolean hasServedTime = p.serveJailTime();
                if (!hasServedTime) {
                    view.handleUpdateView(5, p);
                    handleEndTurnBtn(); // call self
                    return;
                } else {
                    view.handleUpdateView(6, p);
                }
            }

            view.getRollBtn().setEnabled(true);
            view.getPayTaxBtn().setEnabled(true);
            view.getBuyBtn().setEnabled(true);
            diceRolled = false;

            if(getCurrentPlayer().getCurrLocation() instanceof PrivateProperty && ((PrivateProperty) getCurrentPlayer().getCurrLocation()).isOwned()){
                view.handleUpdateView(7, p);
            }else{
                view.handleUpdateView(8, p);
            }

        }else if(!diceRolled){
            view.handleUpdateView(9, getCurrentPlayer());
        }else if(getCurrentPlayer().getCurrLocation() instanceof BankProperty){
            view.handleUpdateView(10, getCurrentPlayer());
        }else {
            view.handleUpdateView(11, getCurrentPlayer());
        }
    }

    private void handlePayTaxBtn(){
        Player p = getCurrentPlayer();
        if(p.getCurrLocation() instanceof BankProperty || p.getCurrLocation() instanceof PrivateProperty && ((PrivateProperty) p.getCurrLocation()).isOwned()){
            if(p.getCurrLocation() instanceof PrivateProperty && ((PrivateProperty) p.getCurrLocation()).getOwner().equals(p)){
                view.handleUpdateView(12, p);
            }else {
                if(payFee() == 0){
                    view.handleUpdateView(13, p);
                    // should not be doing this, feePaid weill be accessible directly from the controller class after handleRollDiceBtn() has been refactored
                    feePaid = false;
                }else{
                    view.handleUpdateView(14, p);
                    // should not be doing this, feePaid weill be accessible directly from the controller class after handleRollDiceBtn() has been refactored
                    feePaid = true;
                }
            }
        }else{
            view.handleUpdateView(15, p);
        }
    }

    private void handleSellBtn() {

    }

    private void handleRollDiceBtn() throws IOException {
        // Calling the rollDie function
        // Added debug comments
        //System.out.println("pressed");
        Player p = getCurrentPlayer();
        view.handleUpdateView(16, p);
        //System.out.println(String.format("INITIAL:\n\tPlayer: %s,\n\tLocation: %s\n", p, p.getCurrLocation()));

        Square oldloc = p.getCurrLocation();
        view.handleUpdateView(17, p);

        roll = rollDie();
        diceRolled = true;
        moveCurrentPlayer();

        view.handleUpdateView(18, p);

        // check if rent or tax need to be paid
        if(p.getCurrLocation() instanceof BankProperty || p.getCurrLocation() instanceof PrivateProperty && ((PrivateProperty) p.getCurrLocation()).isOwned()){
            //System.out.println(String.format("Patrick added this for testing: %s, %s", p.getCurrLocation(), p.getCurrLocation() instanceof PrivateProperty && ((PrivateProperty) p.getCurrLocation()).isOwned()));
            feePaid = false;
        }else{
            feePaid = true;
        }

        // End Game functionality
        if (isGameEnded()) {
            view.handleUpdateView(19, p);

            p = determineWinner();
            view.handleUpdateView(20, p);
            System.exit(-1);
        }

        if(p.getCurrLocation() instanceof PrivateProperty && ((PrivateProperty) p.getCurrLocation()).isOwned()){
            view.handleUpdateView(21, p);
        }else{
            view.handleUpdateView(22, p);
        }

        //System.out.println(String.format("NEW:\n\tPlayer: %s,\n\tLocation: %s, \n\tFeePaid: %s", p, p.getCurrLocation(), feePaid));

        if (getDie().isDouble()) {
            view.handleUpdateView(23, p);
        }

        // Check if the player rolls double three times
        if (isSpeeding()) {
            sendCurrentPlayerToJail();
            view.handleUpdateView(24, p);
            feePaid = true;

            // change handleEndTurnBtn() in the controller back to private after refactoring
            handleEndTurnBtn();
            return;
        }

        if (currentPlayerIsOnGoToJail()) {
            sendCurrentPlayerToJail();
            view.handleUpdateView(25, p);

            // change handleEndTurnBtn() in the controller back to private after refactoring
            handleEndTurnBtn();
            return;
        }

        view.handleDiceViewUpdate(roll[0], roll[1]);

        // Update the new label when the button is clicked
        // Remove 2 labels if available
//        view.getMainPanel().remove(view.getDiceLabel1());
//        view.getMainPanel().remove(view.getDiceLabel2());
//
//        // Stinky code but it works I will refactor later
//        JLabel dieLabel = null;
//        for (int i = 0; i < getDie().getNUM_DICE(); i ++) {
//            InputStream in = getClass().getResourceAsStream(String.format("DiceImg/%d.png", roll[i]));
//            BufferedImage image = ImageIO.read(in);
//            Image resizeImage = image.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
//            dieLabel = new JLabel(new ImageIcon(resizeImage));
//
//            c.gridx = 5 + i;
//            c.gridy = 2;
//            if (i == 0) {
//                diceLabel1 = dieLabel;
//                gb.setConstraints(diceLabel1, c);
//                mainPanel.add(diceLabel1);
//            } else {
//                diceLabel2 = dieLabel;
//                gb.setConstraints(diceLabel2, c);
//                mainPanel.add(diceLabel2);
//            }
//        }
//
//        System.out.println(controller.getCurrentPlayer().getCurrLocation().getIndex());
//
//        endTurnBtn.setEnabled(true);
//
//        mainPanel.validate();
//        mainPanel.repaint();
//
//        // For debugging
//        System.out.println(controller.getCurrentPlayer().getCurrLocation().getIndex());
//        System.out.println(String.format("die 1: %d, die 2: %d", roll[0], roll[1]));
//        System.out.println(controller.getCurrentPlayer().propertiesToString());
    }
}





