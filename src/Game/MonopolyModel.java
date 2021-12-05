package Game;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;

public class MonopolyModel {
    private final int GO_REWARD = 200;

    private ArrayList<Player> players;
    private Board board;
    private Bank bank;
    private Dice die;
    private Player currentPlayer;
    private int consecutiveDoubles;

    // MVC Example
    private MonopolyGUIView view;
    private PlayerPropertyListModel playerPropertyListModel;
    private PlayerPropertyListHouseModel playerPropertyListHouseModel;

    /*
    Objective:
    - Move buyDialog and sellDialog to the view class

    Questions:
    - What do I do with playerPropertyListModel and playerPropertyListHouseModel?

    Assumptions:
    - WRONG: playerPropertyListMode and playerPropertyListHouseModel are useless. I will delete.
     */
    private SellPlayerPropertyDialog sellDialog;
    private BuyHouseHotelDialog buyDialog;

    int[] roll;
    private boolean diceRolled = false;
    private boolean feePaid = true;



    /**
     * Game.MonopolyController constructor
     *
     * @param players, an ArrayList of Game.Player's that are in the game
     */
    public MonopolyModel(ArrayList<Player> players, MonopolyGUIView view) {

        this.players = new ArrayList<>(players);
        this.board = new Board();
        this.bank = new Bank();
        this.die = new Dice();
        //this.currentPlayer = players.get(players.size()-1);
        this.currentPlayer = players.get(0);

        // MVC example
        this.view = view;
        //sellDialog = new SellPlayerPropertyDialog(this.view, this);
        //buyDialog = new BuyHouseHotelDialog(this.view, this);

        for(Player p: this.players){
            p.setCurrLocation(board.getSQUARE(0));
        }
    }

    /**
     * Method to sell the current rail property and returns half the worth of the property to the seller.
     * Method overloading used as both Game.Rail and Game.Business are polymorphs when selling (b/c business may have houses/hotels)
     *
     * @param property, the rail property being sold
     */
    public void sellProperty(RentableAPI property) {
        int amount = property.sell();
        this.currentPlayer.addMoney(amount);
        bank.removeMoney(amount);
    }


    /**
     * Method to purchase the current Game.PrivateProperty, or a house/hotel for the current Game.Business property (cast from the Game.PrivateProperty).
     *
     * If the currentPlayer already owns a full colour set of Game.Business Properties, then they automatically buy one house, iff they already have ...
     * ...four houses on one square, those houses get replaced with one hotel (one hotel is worth more than four houses)
     *
     * @param property, the privateproperty property being sold
     */
    public void purchaseProperty(PrivateProperty property) {
        if (property instanceof Rail) {
            this.currentPlayer.buyPrivateProperty(property);                                            //This adds property and removes money for purchase
            this.bank.addMoney(property.getPrice());                                                    //This adds the money from the purchase to the bank
        }
        //else if (property instanceof Game.Business && !this.currentPlayer.isOwningColorGroup()) {     //Else if the property is Game.Business Property and the currentPlayer does not own a full set; they may buy it
        else if (property instanceof Business){
            this.currentPlayer.buyPrivateProperty(property);                                            //This adds property and removes money for purchase
            this.bank.addMoney(property.getPrice());
        }
    }

    /**
     * Accessor method for the players
     * @return the ArrayList<Game.Player>
     */
    public ArrayList<Player> getPlayers(){ return this.players; }

    /**
     * Mutator method for
     * @param players; the ArrayList<Game.Player> to be assigned
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
     * Gets the next Game.Player in turn circular queue
     * @return  Game.Player, currentPlayer
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
            // Game.Bank property
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
     * @return Game.Player
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
        int cashEarned;

        sellProperty((RentableAPI) property);
        view.updateSquare(property);
        currentPlayer.removeProperty(property);
    }

    public void buyHouses(int index){
        PrivateProperty property = currentPlayer.getPropertyList().get(index);
        if(property instanceof Business){
            if(((Business) property).getNumHouse() < 4){
                ((Business) property).buyHouse();
                currentPlayer.removeMoney((int)(100 + property.getPrice() * 0.1));
                bank.addMoney((int)(100 + property.getPrice() * 0.1));
                view.handleUpdateView(26, currentPlayer);
            }
            else {
                view.handleUpdateView(27, currentPlayer);
            }
        }else{
            view.handleUpdateView(31, currentPlayer);
        }
    }

    public void buyHotels(int index){
        PrivateProperty property = currentPlayer.getPropertyList().get(index);
        if(property instanceof Business){
            if(((Business) property).getNumHotel() == 1){
                view.handleUpdateView(28, currentPlayer);
            }
            else if(((Business) property).getNumHouse() == 4){
                ((Business) property).buyHotel();
                currentPlayer.removeMoney((int)(100 + property.getPrice() * 0.6));
                bank.addMoney((int)(100 + property.getPrice() * 0.6));
                view.handleUpdateView(29, currentPlayer);
            }else{
                view.handleUpdateView(30, currentPlayer);
            }
        }else{
            view.handleUpdateView(31, currentPlayer);
        }
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
                return 2;
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


//    private void handleShowStatsBtn() {
//        int id = getCurrentPlayer().getCurrLocation().getIndex();
//        JOptionPane.showMessageDialog((Component) null,
//                "<html><u>Character info</u>\n" +
//                        "\tCurrent location:- [id: " + id + "] " + getCurrentPlayer().getCurrLocation().getName() +
//                        "\n\tCurrent turn:- " + getCurrentPlayer().getTurn() +
//                        "\n\n<html><u>Asset info</u>\n\tProperties:- \n\t" +
//                        getCurrentPlayer().propertiesToString() +
//                        "\n\tLiquid value:- $" + getCurrentPlayer().getPlayerBalance() +
//                        "\n\tTotal value (property prices included):- $" +
//                        getCurrentPlayer().getPlayerTotalAsset(), "Game.Player " +
//                        getCurrentPlayer().getName() + "'s stats", 1);
//    }

    public void handleBuyBtn(){
        if (getCurrentPlayer().getCurrLocation() instanceof PrivateProperty) {
            if (!((PrivateProperty) getCurrentPlayer().getCurrLocation()).isOwned()) {
                purchaseProperty((PrivateProperty)getCurrentPlayer().getCurrLocation());
                view.handleUpdateView(1, getCurrentPlayer());
                //update label
                view.updateSquare(getCurrentPlayer().getCurrLocation());
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

            //Game.Player p = controller.getNextPlayer();
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
            view.getPayTaxBtn().setEnabled(false);
            view.getBuyBtn().setEnabled(true);
            diceRolled = false;

            if(getCurrentPlayer().getCurrLocation() instanceof PrivateProperty && ((PrivateProperty) getCurrentPlayer().getCurrLocation()).isOwned()){
                view.handleUpdateView(7, p);
            }else{
                view.handleUpdateView(8, p);
            }

            if (currentPlayer instanceof AIPlayer) {
                handleRollDiceBtn();
            }

        }else if(!diceRolled){
            view.handleUpdateView(9, getCurrentPlayer());
        }else if(getCurrentPlayer().getCurrLocation() instanceof BankProperty){
            view.handleUpdateView(10, getCurrentPlayer());
        }else {
            view.handleUpdateView(11, getCurrentPlayer());
        }
    }

    public void handlePayTaxBtn(){
        Player p = getCurrentPlayer();
        if(p.getCurrLocation() instanceof BankProperty || p.getCurrLocation() instanceof PrivateProperty && ((PrivateProperty) p.getCurrLocation()).isOwned()){
            if(p.getCurrLocation() instanceof PrivateProperty && ((PrivateProperty) p.getCurrLocation()).getOwner().equals(p)){
                view.handleUpdateView(12, p);
                feePaid = true;
            }else {
                if(payFee() == 0){
                    view.handleUpdateView(13, p);
                    feePaid = false;
                }else if(payFee() == 1){
                    view.handleUpdateView(14, p);
                    feePaid = true;
                }else if(payFee() == 2){
                    view.handleUpdateView(32, p);
                    feePaid = true;
                }
            }
        }else{
            view.handleUpdateView(15, p);
        }
    }

    public void handleRollDiceBtn() {
        // Calling the rollDie function
        // Added debug comments
        //System.out.println("pressed");
        Player p = getCurrentPlayer();
        view.handleUpdateView(16, p);
        //System.out.println(String.format("INITIAL:\n\tPlayer: %s,\n\tLocation: %s\n", p, p.getCurrLocation()));

        view.handleUpdateView(17, p);

        roll = rollDie();
        diceRolled = true;
        moveCurrentPlayer();

        view.handleUpdateView(18, p);

        // check if rent or tax need to be paid
        if(p.getCurrLocation() instanceof BankProperty || p.getCurrLocation() instanceof PrivateProperty && ((PrivateProperty) p.getCurrLocation()).isOwned()){
            //System.out.println(String.format("Patrick added this for testing: %s, %s", p.getCurrLocation(), p.getCurrLocation() instanceof Game.PrivateProperty && ((Game.PrivateProperty) p.getCurrLocation()).isOwned()));
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
            view.handleUpdateView(17, p);   // clear label (should be "keep all but this player")
            sendCurrentPlayerToJail();
            view.handleUpdateView(18, p);   // update location label
            view.handleUpdateView(24, p);   // dialog
            feePaid = true;

            // change handleEndTurnBtn() in the controller back to private after refactoring
            handleEndTurnBtn();
            return;
        }

        if (currentPlayerIsOnGoToJail()) {
            // added by zak
            view.handleUpdateView(17, p);   // clear label (should be "keep all but this player")
            sendCurrentPlayerToJail();
            view.handleUpdateView(18, p);   // update location label
            view.handleUpdateView(25, p);   // dialog

            // change handleEndTurnBtn() in the controller back to private after refactoring
            handleEndTurnBtn();
            return;
        }

        view.handleDiceViewUpdate(roll[0], roll[1]);

        // If Player is AI, run autoplay() methods and then end turn?
        if (currentPlayer instanceof AIPlayer) {
            //  This doesn't feel right...
            ((AIPlayer)currentPlayer).autoplay();   // we know that rent, tax, or purchase (or not) has been paid
            view.updateSquare(currentPlayer.getCurrLocation());
            feePaid = true;
            //view.handleUpdateView();
            handleEndTurnBtn();
        }

    }

    // FIXME: start of dialog changes
    public void handleSellBtn() {
        view.displaySellDialog();
        //sellDialog = new SellPlayerPropertyDialog(this.view, this, );
        //view.handleSellWindowVisibility(sellDialog);
    }

    public void handleBuyHouseBtn(){
        view.displayBuyHouseDialog();
        //buyDialog = new BuyHouseHotelDialog(this.view, this);
        //view.handleBuyHouseWindowVisibility(buyDialog);
    }

    public void retrieveSellPanelModel(SellPlayerPropertyDialog dialog, PlayerPropertyListModel playerPropertyListModel){
        sellDialog = dialog;
        this.playerPropertyListModel = playerPropertyListModel;
    }

    public void retrieveBuyPanelModel(BuyHouseHotelDialog dialog, PlayerPropertyListHouseModel playerPropertyListHouseModel){
        buyDialog = dialog;
        this.playerPropertyListHouseModel = playerPropertyListHouseModel;
    }

    public void handleDialogSellBtn(){
        int index = sellDialog.getList().getSelectedIndex();
        if (index != -1) {
            sellProperty(index);
            playerPropertyListModel.removeProperty(index);
            updateDialogAfterSellOrBuy();
        }
    }

    public void handleDialogBuyHouseBtn(){
        int index = buyDialog.getList().getSelectedIndex();
        if (index != -1) {
            buyHouses(index);
            updateDialogAfterSellOrBuy();
        }
    }

    public void handleDialogBuyHotelBtn(){
        int index = buyDialog.getList().getSelectedIndex();
        if (index != -1) {
            buyHotels(index);
            updateDialogAfterSellOrBuy();
        }
    }

    private void updateDialogAfterSellOrBuy() {
        Player p = currentPlayer;
        if(p.getCurrLocation() instanceof PrivateProperty && ((PrivateProperty) p.getCurrLocation()).isOwned()){
            view.handleUpdateView(21, p);
        }else{
            view.handleUpdateView(22, p);
        }
    }

    public SellPlayerPropertyDialog getSellDialog() {
        return sellDialog;
    }

    public BuyHouseHotelDialog getBuyDialog() {
        return buyDialog;
    }

    public void setSellDialog(SellPlayerPropertyDialog sellDialog) {
        this.sellDialog = sellDialog;
    }

    public void setBuyHouseHotelDialog(BuyHouseHotelDialog buyDialog) {
        this.buyDialog = buyDialog;
    }
    // FIXME: end of dialog stuff

    /**
     * Parses input xml file to re-name board squares
     * @param filename
     */
    public void importInternationalVersion(String filename) {
        SAXParser saxParser;
        SAXParserFactory fact;

        try {
            fact = SAXParserFactory.newInstance();
            saxParser = fact.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                int i;
                boolean bboard, bsquare, bproperty;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    //super.startElement(uri, localName, qName, attributes);
                    System.out.printf("Start Element: %s\n", qName);
                    if (qName.equalsIgnoreCase("Board")) bboard = true;
                    if (qName.equalsIgnoreCase("Square")) bsquare = true;
                    if (qName.equalsIgnoreCase("Property")) bproperty = true;
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    //super.endElement(uri, localName, qName);
                    System.out.printf("End Element: %s\n", qName);
                    if (qName.equalsIgnoreCase("Board")) bboard = false;
                    if (qName.equalsIgnoreCase("Square")) bsquare = false;
                    if (qName.equalsIgnoreCase("Property")) bproperty = false;
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    //super.characters(ch, start, length);
                    Square square = null;

                    if (i < 38) {
                        square = getBoard().getSQUARE(i);
                    }
                    if (bsquare) {
                        // Update name
                        System.out.println("Square: " + new String(ch, start, length));
                    }

                    if (bproperty) {
                        // Update name and price (if applicable)
                        System.out.println("Property: " + new String(ch, start, length));
                    }
                }
            };
            saxParser.parse(filename, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


