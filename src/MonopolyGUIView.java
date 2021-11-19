/**
 * Date: 2021 - August 11st
 * Contributors:
 * Ngo Huu Gia Bao 101163137
 * Zakaria Ismail 101143497
 * Yuguo Liu 101142730
 * Gabriel Benni Kelley Evensen 101119814
 *
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MonopolyGUIView extends JFrame implements ActionListener{
    private Board board;
    private final JPanel mainPanel;
    private final GridBagLayout gb;
    private final GridBagConstraints c;
    private final ArrayList<JPanel> squares;
    private final ArrayList<JLabel> playerLabels;
    private final JPanel textPanel;
    private final JLabel textLabel;


    private final JButton showStatsBtn;
    private final JButton rollBtn;
    private final JButton buyBtn;
    private final JButton sellBtn;
    private final JButton endTurnBtn;
    private final JButton payTaxBtn;

    private boolean feePaid = true;
    private boolean diceRolled = false;

    //For Roll Dice
    int[] roll;
    private JLabel diceLabel1;
    private JLabel diceLabel2;

    private MonopolyController controller;

    /**
     * Constructor for MonopolyGUIView class
     */
    public MonopolyGUIView(){
        board = new Board();
        gb = new GridBagLayout();
        mainPanel = new JPanel(gb);
        c = new GridBagConstraints();
        squares = new ArrayList<>();
        textPanel = new JPanel();
        textLabel = new JLabel();
        playerLabels = new ArrayList<>();

        ArrayList<Player> players = new ArrayList<>();
        //For running the code, players array list cannot be empty
        players.add(new Player("player1", new Square("GO", 0)));
        players.add(new Player("player2", new Square("GO", 0)));
        players.add(new Player("player3", new Square("GO", 0)));
        players.add(new Player("player4", new Square("GO", 0)));
        controller = new MonopolyController(players, this);
        this.setTitle("Monopoly Game");

        this.showStatsBtn = new JButton();
        this.showStatsBtn.addActionListener(this);

        //MVC Example
        this.buyBtn = new JButton();
        this.buyBtn.addActionListener(controller);

        sellBtn = new JButton();
        sellBtn.addActionListener(this);

        this.endTurnBtn = new JButton();
        this.endTurnBtn.addActionListener(controller);

        this.payTaxBtn = new JButton();
        this.payTaxBtn.addActionListener(controller);

        // Dice Initialization
        this.rollBtn = new JButton();

        // Add rollBtn ActionListener to this class
        this.rollBtn.addActionListener(this);
        this.diceLabel1 = new JLabel();
        this.diceLabel2 = new JLabel();

        textLabel.setText(String.format("<html> %s's turn <br> Location: %s", controller.getCurrentPlayer().getName(), controller.getCurrentPlayer().getCurrLocation().getName()));
    }

    /**
     * This method will create a layout for each square like name, price
     */
    private void SquaresLayout(){
        for(int i = 0; i < 38; i++){
            JPanel squarePanel = new JPanel(new BorderLayout());
            JPanel InfoPanel = new JPanel();
            JPanel playerPanel = new JPanel();

            InfoPanel.setBorder(BorderFactory.createEmptyBorder());
            playerPanel.setBorder(BorderFactory.createEmptyBorder());

            JLabel squareLabel = new JLabel();
            squareLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            squareLabel.setForeground(Color.BLUE);

            JLabel playerLabel = new JLabel();
            playerLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            playerLabel.setForeground(Color.RED);

            playerLabels.add(playerLabel);

            if(board.getSQUARE(i) instanceof PrivateProperty){
                squareLabel.setText(String.format("<html> %s <br> Price: %s </html>", board.getSQUARE(i).getName(), (((PrivateProperty)board.getSQUARE(i)).getPrice())));
            }
            else if(board.getSQUARE(i) instanceof BankProperty){
                squareLabel.setText(String.format("<html> %s <br> Tax: %s </html>", board.getSQUARE(i).getName(), (((BankProperty)board.getSQUARE(i)).getTaxValue())));
            }else{
                squareLabel.setText(board.getSQUARE(i).getName());
            }

            InfoPanel.add(squareLabel);
            playerPanel.add(playerLabel);

            squarePanel.add(InfoPanel, BorderLayout.PAGE_START);
            squarePanel.add(playerPanel, BorderLayout.PAGE_END);

            squares.add(squarePanel);
        }
    }

    /**
     * This method adds the Square into the Board
     */
    private void addSquareToBoard(){
        for(int i = 0; i < 10; i++){
            c.gridx = i;
            c.gridy = 0;

            gb.setConstraints(squares.get(19 + i), c);
            mainPanel.add(squares.get(19 + i));
        }

        for(int i = 0; i < 10; i++) {
            c.gridx = 0;
            c.gridy = i + 1;

            gb.setConstraints(squares.get(18 - i), c);
            mainPanel.add(squares.get(18 - i));
        }

        for(int i = 0; i < 9; i++){
            c.gridx = i + 1;
            c.gridy = 10;

            gb.setConstraints(squares.get(8 - i), c);
            mainPanel.add(squares.get(8 - i));
        }

        for(int i = 0; i < 9; i++){
            c.gridx = 9;
            c.gridy = i + 1;

            gb.setConstraints(squares.get(29 +  i), c);
            mainPanel.add(squares.get(29 +  i));
        }
    }

    /**
     * This method will handle show player's information button, it will display
     * the name of the player, balance, current location, owned properties
     * @param e ActionListener
     */
    private void handleShowStatsBtn(ActionEvent e) {
        int id = this.controller.getCurrentPlayer().getCurrLocation().getIndex();
        JOptionPane.showMessageDialog((Component) null,
            "<html><u>Character info</u>\n" +
                        "\tCurrent location:- [id: " + id + "] " + this.controller.getCurrentPlayer().getCurrLocation().getName() +
                        "\n\tCurrent turn:- " + this.controller.getCurrentPlayer().getTurn() +
                    "\n\n<html><u>Asset info</u>\n\tProperties:- \n\t" +
                    this.controller.getCurrentPlayer().propertiesToString() +
                    "\n\tLiquid value:- $" + this.controller.getCurrentPlayer().getPlayerBalance() +
                    "\n\tTotal value (property prices included):- $" +
                    this.controller.getCurrentPlayer().getPlayerTotalAsset(), "Player " +
                        this.controller.getCurrentPlayer().getName() + "'s stats", 1);
    }

    /**
     * This function will handle the buy property method, it will call the purchaseProperty method from the MonopolyController
     */
//    private void handleBuyPropertyBtn() {
//        Square currentLocation = controller.getCurrentPlayer().getCurrLocation();
//        controller.purchaseProperty((PrivateProperty)currentLocation);
//
//        JOptionPane.showMessageDialog(null, "Successfully buy the property", "Success", JOptionPane.INFORMATION_MESSAGE);
//    }

    /**
     * This function will be executed when the player lands on Square that requires player to pay fees such as
     * Bank Taxes, Rent
     */
    private void handlePayTaxBtn() {
        if(controller.payFee() == 0){
            JOptionPane.showMessageDialog(null, "You do not have enough balance to pay the rent/tax!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
            feePaid = false;
        }else{
            JOptionPane.showMessageDialog(null, "You have successfully paid your rent/tax!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
            feePaid = true;
            payTaxBtn.setEnabled(false);
        }
    }

    /**
     * This method will end the player's current turn and proceed to the next player's turn
     */
//    private void handleEndTurnBtn() {
//        // if tax/rent is not paid, this step will not be reached
//        if(!controller.getDie().isDouble()){
//            // not double
//            controller.getNextPlayer();
//        } else {
//            // is double
//            if (controller.getCurrentPlayer().isInJail()) {
//                // is in jail
//                controller.getNextPlayer();
//            }
//
//
//        }
//
//        //Player p = controller.getNextPlayer();
//        Player p = controller.getCurrentPlayer();
//        // FIXME: make contrroller function?
//        JOptionPane.showMessageDialog(this, String.format("It is %s's turn.", p.getName()));
//        if (p.isInJail()) {
//            boolean hasServedTime = p.serveJailTime();
//            int turnsLeft = 3 - p.getTurnsInJail();
//            if (!hasServedTime) {
//                JOptionPane.showMessageDialog(this,
//                        String.format("Skipping %s's turn. Player is in Jail with %d turns remaining.", p.getName(), turnsLeft));
//                handleEndTurnBtn(); // call self
//                return;
//            } else {
//                JOptionPane.showMessageDialog(this, "%s's jail time has been served." +
//                        "They may play this turn.");
//            }
//        }
//
//        rollBtn.setEnabled(true);
//        payTaxBtn.setEnabled(true);
//        buyBtn.setEnabled(true);
//        diceRolled = false;
//
//        if(controller.getCurrentPlayer().getCurrLocation() instanceof PrivateProperty && ((PrivateProperty) controller.getCurrentPlayer().getCurrLocation()).isOwned()){
//            textLabel.setText(String.format("<html> %s's turn <br> Location: %s <br> Owner: %s", controller.getCurrentPlayer().getName(), controller.getCurrentPlayer().getCurrLocation().getName(), ((PrivateProperty) controller.getCurrentPlayer().getCurrLocation()).getOwner().getName()));
//        }else{
//            textLabel.setText(String.format("<html> %s's turn <br> Location: %s", controller.getCurrentPlayer().getName(), controller.getCurrentPlayer().getCurrLocation().getName()));
//        }
//    }


    /**
     * Method handles roll dice button. It will delete the 2 labels next to the
     * Roll Dice button and update the 2 new die face images corresponding to the
     * value that player rolls the dice
     * @throws IOException
     */
    private void handleRollDiceBtn() throws IOException {
        // Calling the rollDie function
        // Added debug comments
//        rollBtn.setEnabled(false);
//        Player p = controller.getCurrentPlayer();
//        System.out.println(String.format("INITIAL:\n\tPlayer: %s,\n\tLocation: %s\n", p, p.getCurrLocation()));
//
//        Square oldloc = controller.getCurrentPlayer().getCurrLocation();
//        playerLabels.get(controller.getCurrentPlayer().getCurrLocation().getIndex()).setText("");
//
//        roll = controller.rollDie();
//        diceRolled = true;
//        controller.moveCurrentPlayer();
//
//        StringBuilder str = new StringBuilder();
//        for (Player playa : oldloc.getPlayersCurrentlyOn()) {
//            str.append("%s\n");
//        }
//        playerLabels.get(oldloc.getIndex()).setText(String.valueOf(str));
//        playerLabels.get(controller.getCurrentPlayer().getCurrLocation().getIndex()).setText(controller.getCurrentPlayer().getName());
//
//        // check if rent or tax need to be paid
//        if(controller.getCurrentPlayer().getCurrLocation() instanceof BankProperty || controller.getCurrentPlayer().getCurrLocation() instanceof PrivateProperty && ((PrivateProperty) controller.getCurrentPlayer().getCurrLocation()).isOwned()){
//            System.out.println(String.format("Patrick added this for testing: %s, %s", controller.getCurrentPlayer().getCurrLocation(), controller.getCurrentPlayer().getCurrLocation() instanceof PrivateProperty && ((PrivateProperty) controller.getCurrentPlayer().getCurrLocation()).isOwned()));
//            feePaid = false;
//        }else{
//            feePaid = true;
//        }
//
//        // End Game functionality
//        if (controller.isGameEnded()) {
//            JOptionPane.showMessageDialog(this, String.format("%s cannot afford this fee.\n Bankrupt!", p.getName()) +
//                    "Game is Over");
//            Player winner = controller.determineWinner();
//            JOptionPane.showMessageDialog(this, String.format("%s is the winner with total value of $%d",
//                    winner.getName(), winner.getPlayerTotalAsset()));
//
//            //Exit the game
//            this.dispose();
//            System.exit(-1);
//        }
//
//        if(controller.getCurrentPlayer().getCurrLocation() instanceof PrivateProperty && ((PrivateProperty) controller.getCurrentPlayer().getCurrLocation()).isOwned()){
//            textLabel.setText(String.format("<html> %s's turn <br> Location: %s <br> Owner: %s", controller.getCurrentPlayer().getName(), controller.getCurrentPlayer().getCurrLocation().getName(), ((PrivateProperty) controller.getCurrentPlayer().getCurrLocation()).getOwner().getName()));
//        }else{
//            textLabel.setText(String.format("<html> %s's turn <br> Location: %s", controller.getCurrentPlayer().getName(), controller.getCurrentPlayer().getCurrLocation().getName()));
//        }
//
//        System.out.println(String.format("NEW:\n\tPlayer: %s,\n\tLocation: %s, \n\tFeePaid: %s", p, p.getCurrLocation(), feePaid));
//
//        if (controller.getDie().isDouble()) {
//            JOptionPane.showMessageDialog(this, String.format("%s has rolled a DOUBLE!", p.getName()));
//        }
//
//        // Check if the player rolls double three times
//        if (controller.isSpeeding()) {
//            controller.sendCurrentPlayerToJail();
//            JOptionPane.showMessageDialog(this, String.format("%s has been caught SPEEDING!", p.getName()) +
//                    "They have been sent to jail and their turn shall be skipped for 3 rounds.");
//            feePaid = true;
//
//            // change handleEndTurnBtn() in the controller back to private after refactoring
//            controller.handleEndTurnBtn();
//            return;
//        }
//
//        if (controller.currentPlayerIsOnGoToJail()) {
//            controller.sendCurrentPlayerToJail();
//            JOptionPane.showMessageDialog(this, String.format("%s is on Go To Jail. Turn Ended.", p.getName()));
//
//            // change handleEndTurnBtn() in the controller back to private after refactoring
//            controller.handleEndTurnBtn();
//            return;
//        }

        // Update the new label when the button is clicked
        // Remove 2 labels if available
//        mainPanel.remove(diceLabel1);
//        mainPanel.remove(diceLabel2);
//
//        // Stinky code but it works I will refactor later
//        JLabel dieLabel = null;
//        for (int i = 0; i < controller.getDie().getNUM_DICE(); i ++) {
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

    public void updateDiceFaces(int face1, int face2) throws IOException {
        roll = new int[]{face1, face2};
        // Update the new label when the button is clicked
        // Remove 2 labels if available
        mainPanel.remove(diceLabel1);
        mainPanel.remove(diceLabel2);

        // Stinky code but it works I will refactor later
        JLabel dieLabel = null;
        for (int i = 0; i < controller.getDie().getNUM_DICE(); i ++) {
            InputStream in = getClass().getResourceAsStream(String.format("DiceImg/%d.png", roll[i]));
            BufferedImage image = ImageIO.read(in);
            Image resizeImage = image.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            dieLabel = new JLabel(new ImageIcon(resizeImage));

            c.gridx = 5 + i;
            c.gridy = 2;
            if (i == 0) {
                diceLabel1 = dieLabel;
                gb.setConstraints(diceLabel1, c);
                mainPanel.add(diceLabel1);
            } else {
                diceLabel2 = dieLabel;
                gb.setConstraints(diceLabel2, c);
                mainPanel.add(diceLabel2);
            }
        }

        System.out.println(controller.getCurrentPlayer().getCurrLocation().getIndex());

        endTurnBtn.setEnabled(true);

        mainPanel.validate();
        mainPanel.repaint();

        // For debugging
        System.out.println(controller.getCurrentPlayer().getCurrLocation().getIndex());
        System.out.println(String.format("die 1: %d, die 2: %d", roll[0], roll[1]));
        System.out.println(controller.getCurrentPlayer().propertiesToString());
    }

    /**
     * This method will handle the sell button
     */
    private void handleSellBtn() {
        System.out.println("Sell btn pressed!");
        SellPlayerPropertyDialog sppd = new SellPlayerPropertyDialog(this, controller);
        sppd.setVisible(true);
    }

    /**
     * This method will add buttons, text box to the middle of the board
     */
    private void addButtonToBoard(){
        // Text Panel
        c.gridx = 2;
        c.gridy = 4;
        gb.setConstraints(textPanel, c);
        textPanel.setBorder(BorderFactory.createEmptyBorder());
        textLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textLabel.setForeground(Color.RED);

        textLabel.setMinimumSize(new Dimension(200,200));
        textLabel.setPreferredSize(new Dimension(200,200));
        textLabel.setMaximumSize(new Dimension(200,200));

        textPanel.add(textLabel);
        mainPanel.add(textPanel);

        // Show Stats button
        c.gridy = 2;
        c.gridwidth = 4;
        c.gridheight = 2;

        gb.setConstraints(showStatsBtn, c);
        showStatsBtn.setText("Show Stats");
        showStatsBtn.setForeground(Color.RED);
        mainPanel.add(showStatsBtn);

        // Roll Button
        c.gridx = 3;
        gb.setConstraints(rollBtn, c);
        rollBtn.setText("Roll Dice");
        rollBtn.setForeground(Color.RED);
        mainPanel.add(rollBtn);

        // Dice Label
        c. gridx = 5;
        diceLabel1.setText("Click Roll Dice to see the magic");
        gb.setConstraints(diceLabel1, c);
        mainPanel.add(diceLabel1);

        // Buy Button
        c.gridx = 3;
        c.gridy = 3;
        gb.setConstraints(buyBtn, c);
        buyBtn.setText("Buy Property");
        buyBtn.setForeground(Color.RED);
        mainPanel.add(buyBtn);

        // Sell Button
        c.gridy = 4;
        gb.setConstraints(sellBtn, c);
        sellBtn.setText("Sell Property");
        sellBtn.setForeground(Color.RED);
        mainPanel.add(sellBtn);

        // payTax Button
        c.gridy = 5;
        gb.setConstraints(payTaxBtn, c);
        payTaxBtn.setText("Pay Tax/Rent");
        payTaxBtn.setForeground(Color.RED);
        mainPanel.add(payTaxBtn);

        // endTurn Button
        c.gridy = 6;
        gb.setConstraints(endTurnBtn, c);
        endTurnBtn.setText("End Turn");
        endTurnBtn.setForeground(Color.RED);
        mainPanel.add(endTurnBtn);
    }

    /**
     * The method displays the GUI
     */
    public void displayGUI(){
        this.SquaresLayout();
        this.addSquareToBoard();

        this.addButtonToBoard();

        this.add(mainPanel);

        this.setPreferredSize(new Dimension(2000, 2000));
        this.pack();
        // will make the GUI displays in the middle screen : )
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        MonopolyGUIView self = this;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (JOptionPane.showConfirmDialog(self, "Are you sure you want to quit?")
                        == JOptionPane.OK_OPTION) {
                    self.setVisible(false);
                    self.dispose();

                    // Quit the program
                    System.exit(-1);
                }
            }
        });

        this.setVisible(true);
    }

    /**
     * This method will check what button is pressed and calls its handle method
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == showStatsBtn) {
            handleShowStatsBtn(e);
        }

//        else if (e.getSource() == rollBtn) {
//            try {
//                handleRollDiceBtn();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }

        else if (e.getSource() == sellBtn) {
            handleSellBtn();
        }

//        else if (e.getSource() == buyBtn) {
//            if (controller.getCurrentPlayer().getCurrLocation() instanceof PrivateProperty) {
//                if (!((PrivateProperty) controller.getCurrentPlayer().getCurrLocation()).isOwned()) {
//                    handleBuyPropertyBtn();
//                } else {
//                    JOptionPane.showMessageDialog(null, "This Property is already owned!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
//                }
//            } else {
//                JOptionPane.showMessageDialog(null, "There is not a purchasable property!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
//            }
//        }

//        else if (e.getSource() == payTaxBtn) {
//            if(controller.getCurrentPlayer().getCurrLocation() instanceof BankProperty || controller.getCurrentPlayer().getCurrLocation() instanceof PrivateProperty && ((PrivateProperty) controller.getCurrentPlayer().getCurrLocation()).isOwned()){
//                if(controller.getCurrentPlayer().getCurrLocation() instanceof PrivateProperty && ((PrivateProperty) controller.getCurrentPlayer().getCurrLocation()).getOwner().equals(controller.getCurrentPlayer())){
//                    JOptionPane.showMessageDialog(null, "You own this property, no rent to be paid!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
//                }else {
//                    handlePayTaxBtn();
//                }
//            }else{
//                JOptionPane.showMessageDialog(null, "There is no tax/rent to pay!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
//            }
//        }
//        else if (e.getSource() == endTurnBtn) {
//            if(feePaid && diceRolled) {
//                handleEndTurnBtn();
//            }else if(!diceRolled){
//                JOptionPane.showMessageDialog(null, "You must roll the dice before ending the turn!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
//            }else if(controller.getCurrentPlayer().getCurrLocation() instanceof BankProperty){
//                JOptionPane.showMessageDialog(null, "You have not paid your tax yet!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
//            }else {
//                JOptionPane.showMessageDialog(null, "You have not paid your rent yet!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
//            }
//        }
    }



    //MVC example
    public ArrayList<JLabel> getPlayerLabels(){
        return this.playerLabels;
    }

    public JLabel getTextLabel() {
        return textLabel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JLabel getDiceLabel1() {
        return diceLabel1;
    }

    public JLabel getDiceLabel2() {
        return diceLabel2;
    }

    public JButton getBuyBtn(){
        return buyBtn;
    }

    public JButton getEndTurnBtn(){
        return endTurnBtn;
    }

    public JButton getRollBtn(){
        return rollBtn;
    }

    public JButton getPayTaxBtn(){
        return payTaxBtn;
    }

    // These 4 method should not be here, when handleRollDiceBtn is refactored into the controller, tha attribute "diceRolled" and "feePaid" should also be moved into the controller
    public boolean getDiceRolled(){
        return diceRolled;
    }
    public void setDiceRolled(boolean status){
        diceRolled = status;
    }
    public boolean getFeePaid(){
        return feePaid;
    }
    public void setFeePaid(boolean status){
        feePaid = status;
    }

    public void handleUpdateView(int dialogNum, Player player){
        if(dialogNum == 1){
            JOptionPane.showMessageDialog(null, "Successfully buy the property", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(dialogNum == 2){
            JOptionPane.showMessageDialog(null, "This Property is already owned!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(dialogNum == 3){
            JOptionPane.showMessageDialog(null, "There is not a purchasable property!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(dialogNum == 4){
            JOptionPane.showMessageDialog(null, String.format("It is %s's turn.", player.getName()));
        }
        else if(dialogNum == 5){
            int turnsLeft = 3 - player.getTurnsInJail();
            JOptionPane.showMessageDialog(null,
                    String.format("Skipping %s's turn. Player is in Jail with %d turns remaining.", player.getName(), turnsLeft));
        }
        else if(dialogNum == 6){
            JOptionPane.showMessageDialog(null, "%s's jail time has been served." +
                    "They may play this turn.");
        }
        else if(dialogNum == 7){
            textLabel.setText(String.format("<html> %s's turn <br> Location: %s <br> Owner: %s", player.getName(), player.getCurrLocation().getName(), ((PrivateProperty) player.getCurrLocation()).getOwner().getName()));
        }
        else if(dialogNum == 8){
            textLabel.setText(String.format("<html> %s's turn <br> Location: %s", player.getName(), player.getCurrLocation().getName()));
        }
        else if(dialogNum == 9){
            JOptionPane.showMessageDialog(null, "You must roll the dice before ending the turn!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(dialogNum == 10){
            JOptionPane.showMessageDialog(null, "You have not paid your tax yet!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(dialogNum == 11){
            JOptionPane.showMessageDialog(null, "You have not paid your rent yet!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
        }else if(dialogNum == 12) {
            JOptionPane.showMessageDialog(null, "You own this property, no rent to be paid!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
        }else if(dialogNum == 13){
            JOptionPane.showMessageDialog(null, "You do not have enough balance to pay the rent/tax!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
        }else if(dialogNum == 14){
            JOptionPane.showMessageDialog(null, "You have successfully paid your rent/tax!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
            payTaxBtn.setEnabled(false);
        }else if(dialogNum == 15){
            JOptionPane.showMessageDialog(null, "There is no tax/rent to pay!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        MonopolyGUIView view = new MonopolyGUIView();
        view.displayGUI();
    }
}



