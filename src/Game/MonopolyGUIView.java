package Game; /**
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MonopolyGUIView extends JFrame {
    private Board board;
    private JPanel headerPanel;
    private final JPanel mainPanel;
    private final JPanel remotePanel;
    private final GridBagLayout gb;
    private final GridBagConstraints c;
    private final ArrayList<JPanel> squares;
    private final ArrayList<JLabel> playerLabels;
    private final JPanel textPanel;
    private final JLabel textLabel;


//    private final JButton showStatsBtn;
    private final JButton rollBtn;
    private final JButton buyBtn;
    private final JButton sellBtn;
    private final JButton endTurnBtn;
    private final JButton payTaxBtn;
    private final JButton buyHouseBtn;

    private ArrayList<JLabel>labelList;

    //For Roll Game.Dice
    int[] roll;
    private JLabel diceLabel1;
    private JLabel diceLabel2;

    private JLabel houseLabel;
    private JLabel hotelLabel;

    private SellPlayerPropertyDialog sellDialog;
    private BuyHouseHotelDialog buyDialog;

    private MonopolyController controller;
    private MonopolyModel model;

    //Menu Bar
    private JMenuBar menuBar;
    private JMenu fileMenu;

    private JMenuItem saveItem;
    private JMenuItem loadItem;
    private JMenuItem importInternationalItem;

    /**
     * Constructor for Game.MonopolyGUIView class
     */
    public MonopolyGUIView(){
        board = new Board();
        gb = new GridBagLayout();
        headerPanel = new JPanel(new FlowLayout());
        mainPanel = new JPanel(gb);
        remotePanel = new JPanel(gb);
        c = new GridBagConstraints();
        squares = new ArrayList<>();
        textPanel = new JPanel();
        textLabel = new JLabel();
        playerLabels = new ArrayList<>();

        labelList = new ArrayList<>();

        JPanel infoPanel = new JPanel(new GridLayout(2,1));

        JTextField nAIPlayers = new JTextField("# AI Players");
        JTextField nHumanPlayers = new JTextField("# Human Players");

        infoPanel.add(nAIPlayers);
        infoPanel.add(nHumanPlayers);

        ArrayList<Player> players = new ArrayList<>();
        int option = JOptionPane.showConfirmDialog(null, infoPanel, "Game setup",JOptionPane.OK_CANCEL_OPTION);
        if(option == JOptionPane.OK_OPTION){
            //adding human players
            for (int i = 0; i < Integer.parseInt(nHumanPlayers.getText()); i++){
                players.add(new Player("Player " + i + 1, new Square("GO", 0)));
            }
            //adding AI players
            for (int i = 0; i < Integer.parseInt(nAIPlayers.getText()); i++) {
                players.add(new AIPlayer("AI Player " + i + 1, new Square("GO", 0)));
            }
        }else{
            //start off with 4 AI players if not cancel option is selected
            players.add(new AIPlayer("Player 1", new Square("GO", 0)));
            players.add(new AIPlayer("Player 2", new Square("GO", 0)));
            players.add(new AIPlayer("Player 3", new Square("GO", 0)));
            players.add(new AIPlayer("Player 4", new Square("GO", 0)));
        }
        //this.controller = new MonopolyController(players, this);
        this.model = new MonopolyModel(players, this);
        this.controller = new MonopolyController(this.model, this);

        this.setTitle("Monopoly Game");

//        this.showStatsBtn = new JButton();
//        this.showStatsBtn.addActionListener(controller);

        //MVC Example
        this.buyBtn = new JButton();
        this.buyBtn.addActionListener(controller);

        sellBtn = new JButton();
        sellBtn.addActionListener(controller);

        this.endTurnBtn = new JButton();
        this.endTurnBtn.addActionListener(controller);

        this.payTaxBtn = new JButton();
        this.payTaxBtn.addActionListener(controller);

        this.buyHouseBtn = new JButton();
        this.buyHouseBtn.addActionListener(controller);

        // Game.Dice Initialization
        this.rollBtn = new JButton();

        // Add rollBtn ActionListener to this class
        this.rollBtn.addActionListener(controller);
        this.diceLabel1 = new JLabel();
        this.diceLabel2 = new JLabel();

        textLabel.setText(displayPlayerInfo());

        this.houseLabel = new JLabel();
        this.hotelLabel = new JLabel();

        // Menu Part
        this.menuBar = new JMenuBar();
        this.fileMenu = new JMenu("File");

        // Save Menu Item
        this.saveItem = new JMenuItem("Save");
        this.saveItem.addActionListener(controller);
        this.fileMenu.add(saveItem);

        // Load Menu Item
        this.loadItem = new JMenuItem("Load");
        this.loadItem.addActionListener(controller);
        this.fileMenu.add(loadItem);

        // Import International Menu Item
        this.importInternationalItem = new JMenuItem("Import International Version");
        this.importInternationalItem.addActionListener(controller);
        this.fileMenu.add(importInternationalItem);

        this.headerPanel.add(menuBar);
        this.headerPanel.setBackground(new Color(166, 223, 242));
        this.add(headerPanel, BorderLayout.NORTH);

    }

    private String displayPlayerInfo() {
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("<html><u>Player Info</u>:-<br>")
                .append(model.getCurrentPlayer().getName())
                .append("'s turn <br> Location: ")
                .append(model.getCurrentPlayer().getCurrLocation().getName())
                .append("<br><br><u>Asset Info</u>:-<br> Properties owned:<br>")
                .append(model.getCurrentPlayer().propertiesToString())

                .append("<br><br><u>Monetary Info</u>:-<br> Total asset value: $")
                .append(model.getCurrentPlayer().getPlayerTotalAsset())
                .append("<br>Liquid value: $")
                .append(model.getCurrentPlayer().getPlayerBalance());

        return stringBuilder.toString();
    }

    private String displayHouseHotel() {
        StringBuilder houseText = new StringBuilder("House: ");
        StringBuilder hotelText = new StringBuilder("Hotel: ");
        Player currentPlayer = model.getCurrentPlayer();

        for (PrivateProperty property : currentPlayer.getPropertyList()) {
            if (((Business) property).getNumHouse() > 0) {
                int numHouse = ((Business) property).getNumHouse();
                houseText.append(numHouse);
            } else if (((Business) property).getNumHotel() > 0) {
                int numHotel = ((Business) property).getNumHouse();
                hotelText.append(numHotel);
            }
        }
        StringBuilder sb = new StringBuilder("");

        sb.append(houseText).append("<br>").append(hotelText);
        return sb.toString();
    }

    /**
     * This method will create a layout for each square like name, price
     */
    private void squaresLayout() {
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

            labelList.add(squareLabel);

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

            squarePanel.add(InfoPanel, BorderLayout.CENTER);
            squarePanel.add(playerPanel, BorderLayout.SOUTH);

            squares.add(squarePanel);
        }
    }


    /**
     * This method adds the Game.Square into the Game.Board
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
     * Method handles roll dice button. It will delete the 2 labels next to the
     * Roll Game.Dice button and update the 2 new die face images corresponding to the
     * value that player rolls the dice
     * @throws IOException
     */
    public void updateDiceFaces(int face1, int face2) {

        roll = new int[]{face1, face2};

        // Update the new label when the button is clicked
        // Remove 2 labels if available
        mainPanel.remove(diceLabel1);
        mainPanel.remove(diceLabel2);

        // Stinky code but it works I will refactor later
        JLabel dieLabel = null;
        for (int i = 0; i < model.getDie().getNUM_DICE(); i ++) {
            try {
                InputStream in = getClass().getResourceAsStream(String.format("../DiceImg/%d.png", roll[i]));
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
            }catch (Exception e){
                System.out.println("Image related with dice face " + i + " not found");
            }
        }

        System.out.println(model.getCurrentPlayer().getCurrLocation().getIndex());

        endTurnBtn.setEnabled(true);

        mainPanel.validate();
        mainPanel.repaint();

        // For debugging
        System.out.println(model.getCurrentPlayer().getCurrLocation().getIndex());
        System.out.println(String.format("die 1: %d, die 2: %d", roll[0], roll[1]));
        System.out.println(model.getCurrentPlayer().propertiesToString());
    }

    /**
     * This method will update the square when player owns that square
     * @param square
     */
    public void updateSquare(Square square){
        //String playerName = ((PrivateProperty) square).getOwner().getName();
        Player owner = ((PrivateProperty) square).getOwner();
        JLabel newLabel = labelList.get(square.getIndex());

        if (owner != null) {
            String playerName = owner.getName();
            newLabel.setText(String.format("<html> %s <br> Price: %s <br> Ownership: %s</html>",
                    square.getName(), ((PrivateProperty) square).getPrice(),
                    playerName));
        } else {
            newLabel.setText(String.format("<html> %s <br> Price: %s</html>",
                    square.getName(), ((PrivateProperty) square).getPrice()));
        }
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
        textLabel.setPreferredSize(new Dimension(200,300));
        textLabel.setMaximumSize(new Dimension(200,1000));

        textPanel.add(textLabel);
        mainPanel.add(textPanel);

        // Show Stats button
        c.gridy = 2;
        c.gridwidth = 4;
        c.gridheight = 2;

//        gb.setConstraints(showStatsBtn, c);
//        showStatsBtn.setText("Show Stats");
//        showStatsBtn.setForeground(Color.RED);
//        mainPanel.add(showStatsBtn);

        // Roll Button
        c.gridx = 3;
        gb.setConstraints(rollBtn, c);
        rollBtn.setText("Roll Dice");
        rollBtn.setForeground(Color.RED);
        mainPanel.add(rollBtn);

        // Game.Dice Label
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

        //buyHouse Button
        c.gridy = 6;
        gb.setConstraints(buyHouseBtn, c);
        buyHouseBtn.setText("Buy House/Hotel");
        buyHouseBtn.setForeground(Color.RED);
        mainPanel.add(buyHouseBtn);

        // endTurn Button
        c.gridy = 7;
        gb.setConstraints(endTurnBtn, c);
        endTurnBtn.setText("End Turn");
        endTurnBtn.setForeground(Color.RED);
        mainPanel.add(endTurnBtn);
    }

    /**
     * The method displays the GUI
     */
    public void displayGUI() throws IOException {
        this.squaresLayout();
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


    //MVC example
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

    public JButton getSellBtn() {
        return sellBtn;
    }

//    public JButton getShowStatsBtn() {
//        return showStatsBtn;
//
//    }
    public JButton getBuyHouseBtn(){
        return buyHouseBtn;
    }

    public void handleUpdateView(int dialogNum, Player player){
        if(dialogNum == 1){
            JOptionPane.showMessageDialog(null, "Successfully buy the property", "Success", JOptionPane.INFORMATION_MESSAGE);
            textLabel.setText(String.format("<html><u>Player Info</u>:-<br> %s's turn <br> Location: %s <br> Owner: %s <br><br><u>Property Info</u>:-<br> Properties owned:<br> %s <br><br>Monetary Info:-<br> Total asset value: $%d <br>Liquid value: $%d", player.getName(), player.getCurrLocation().getName(), ((PrivateProperty) player.getCurrLocation()).getOwner().getName(), player.propertiesToString(), player.getPlayerTotalAsset(), player.getPlayerBalance()));
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
                    String.format("Skipping %s's turn, they are in Jail with %d turns remaining.", player.getName(), turnsLeft));
        }
        else if(dialogNum == 6){
            JOptionPane.showMessageDialog(null, "%s's jail time has been served." +
                    "They may play this turn.");
        }
        else if(dialogNum == 7){
            textLabel.setText(String.format("<html><u>Player Info</u>:-<br> %s's turn <br> Location: %s <br> Owner: %s <br><br><u>Property Info</u>:-<br> Properties owned:<br> %s <br><br>Monetary Info:-<br> Total asset value: $%d <br>Liquid value: $%d", player.getName(), player.getCurrLocation().getName(), ((PrivateProperty) player.getCurrLocation()).getOwner().getName(), player.propertiesToString(), player.getPlayerTotalAsset(), player.getPlayerBalance()));
        }
        else if(dialogNum == 8){
            textLabel.setText(String.format("<html><u>Player Info</u>:-<br> %s's turn <br> Location: %s <br><u>Property Info</u>:-<br> Properties owned:<br> %s <br><br>Monetary Info:-<br> Total asset value: $%d <br>Liquid value: $%d", player.getName(), player.getCurrLocation().getName(), player.propertiesToString(), player.getPlayerTotalAsset(), player.getPlayerBalance()));
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
            JOptionPane.showMessageDialog(null, "You have successfully paid your rent!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
            textLabel.setText(String.format("<html><u>Player Info</u>:-<br> %s's turn <br> Location: %s <br> Owner: %s <br><br><u>Property Info</u>:-<br> Properties owned:<br> %s <br><br>Monetary Info:-<br> Total asset value: $%d <br>Liquid value: $%d", player.getName(), player.getCurrLocation().getName(), ((PrivateProperty) player.getCurrLocation()).getOwner().getName(), player.propertiesToString(), player.getPlayerTotalAsset(), player.getPlayerBalance()));
            payTaxBtn.setEnabled(false);
        }else if(dialogNum == 15){
            JOptionPane.showMessageDialog(null, "There is no tax/rent to pay!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
        }else if(dialogNum == 16){
            rollBtn.setEnabled(false);
            payTaxBtn.setEnabled(true);
        }else if(dialogNum == 17){
            //zak: modifying it so that instead of clearing the label, it keeps everyone BUT the input player
            //playerLabels.get(player.getCurrLocation().getIndex()).setText("");
            Square loc = player.getCurrLocation();
            int index = loc.getIndex();
            JLabel label = playerLabels.get(index);
            label.setText("");

            StringBuilder str = new StringBuilder();
            for (Player p : loc.getPlayersCurrentlyOn()) {
                if (p != player) {
                    str.append(p.getName()).append("\n");
                }
            }

            label.setText(str.toString());
        }else if(dialogNum == 18){
            // updates square so that it includes everyone?
            // zak: fixed up a bit
            StringBuilder str = new StringBuilder();
            for (Player playa : player.getCurrLocation().getPlayersCurrentlyOn()) {
                str.append(playa.getName());
            }
            //playerLabels.get(player.getCurrLocation().getIndex()).setText(str.toString());
            //playerLabels.get(player.getCurrLocation().getIndex()).setText(String.valueOf(str));
            // Commented by zak
            playerLabels.get(player.getCurrLocation().getIndex()).setText(player.getName());
        }else if(dialogNum == 19){
            JOptionPane.showMessageDialog(null, String.format("%s cannot afford this fee.\n Bankrupt!", player.getName()) +
                    "Game is Over");
        }else if(dialogNum == 20){
            JOptionPane.showMessageDialog(null, String.format("%s is the winner with total value of $%d",
                    player.getName(), player.getPlayerTotalAsset()));
            this.dispose();
        }else if(dialogNum == 21){
            // land owned private property
            textLabel.setText(String.format("<html><u>Player Info</u>:-<br> %s's turn <br> Location: %s <br> Owner: %s <br><br><u>Property Info</u>:-<br> Properties owned:<br> %s <br><br>Monetary Info:-<br> Total asset value: $%d <br>Liquid value: $%d", player.getName(), player.getCurrLocation().getName(), ((PrivateProperty) player.getCurrLocation()).getOwner().getName(), player.propertiesToString(), player.getPlayerTotalAsset(), player.getPlayerBalance()));
        }else if(dialogNum == 22){
            // land on anything else
            textLabel.setText(String.format("<html><u>Player Info</u>:-<br> %s's turn <br> Location: %s <br><br><u>Property Info</u>:-<br> Properties owned:<br> %s <br><br>Monetary Info:-<br> Total asset value: $%d <br>Liquid value: $%d", player.getName(), player.getCurrLocation().getName(), player.propertiesToString(), player.getPlayerTotalAsset(), player.getPlayerBalance()));
        }else if(dialogNum == 23){
            JOptionPane.showMessageDialog(null, String.format("%s has rolled a DOUBLE!", player.getName()));
        }else if(dialogNum == 24){
            JOptionPane.showMessageDialog(null, String.format("%s has been caught SPEEDING!", player.getName()) +
                    "They have been sent to jail and their turn shall be skipped for 3 rounds.");
            textLabel.setText(String.format("<html><u>Player Info</u>:-<br> %s's turn <br> Location: %s <br> Owner: %s <br><br><u>Property Info</u>:-<br> Properties owned:<br> %s <br><br>Monetary Info:-<br> Total asset value: $%d <br>Liquid value: $%d", player.getName(), player.getCurrLocation().getName(), ((PrivateProperty) player.getCurrLocation()).getOwner().getName(), player.propertiesToString(), player.getPlayerTotalAsset(), player.getPlayerBalance()));
        }else if(dialogNum == 25){
            JOptionPane.showMessageDialog(null, String.format("%s is on Go To Jail. Turn Ended.", player.getName()));
        }else if(dialogNum == 26){
            JOptionPane.showMessageDialog(null, "Successfully bought a house on this property", "Alert!", JOptionPane.INFORMATION_MESSAGE);
            textLabel.setText(displayPlayerInfo());

        }else if(dialogNum == 27){
            JOptionPane.showMessageDialog(null, "You already have 4 houses on this property, can not buy more!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
        }else if(dialogNum == 28){
            JOptionPane.showMessageDialog(null, "You already have 1 hotel on this property, can not buy more!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
        }else if(dialogNum == 29){
            JOptionPane.showMessageDialog(null, "Successfully bought a hotel on this property", "Alert!", JOptionPane.INFORMATION_MESSAGE);
            textLabel.setText(displayPlayerInfo());

        }else if(dialogNum == 30) {
            JOptionPane.showMessageDialog(null, "You need have 4 houses on this property in order to buy a hotel, you currently do not meet this requirement", "Alert!", JOptionPane.INFORMATION_MESSAGE);
        }else if(dialogNum == 31){
            JOptionPane.showMessageDialog(null, "You can not buy houses or hotels on a Rail property!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
        }else if(dialogNum == 32){
            JOptionPane.showMessageDialog(null, "You have successfully paid your tax!", "Alert!", JOptionPane.INFORMATION_MESSAGE);
            textLabel.setText(String.format("<html><u>Player Info</u>:-<br> %s's turn <br> Location: %s <br><br><u>Property Info</u>:-<br> Properties owned:<br> %s <br><br>Monetary Info:-<br> Total asset value: $%d <br>Liquid value: $%d", player.getName(), player.getCurrLocation().getName(), player.propertiesToString(), player.getPlayerTotalAsset(), player.getPlayerBalance()));
            payTaxBtn.setEnabled(false);
        }
    }

    public void handleDiceViewUpdate(int roll1, int roll2){
        updateDiceFaces(roll1, roll2);
    }

    public void handleSellWindowVisibility(SellPlayerPropertyDialog window){
        window.setVisible(true);
    }

    public void handleBuyHouseWindowVisibility(BuyHouseHotelDialog window){
        window.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        MonopolyGUIView view = new MonopolyGUIView();
        view.displayGUI();
    }


    // FIXME: start of dialog changes
    public void displaySellDialog() {
        sellDialog = new SellPlayerPropertyDialog(this, this.model, this.controller);
        model.setSellDialog(sellDialog);
        handleSellWindowVisibility(sellDialog);
    }

    public void displayBuyHouseDialog(){
        buyDialog = new BuyHouseHotelDialog(this, this.model, this.controller);
        model.setBuyHouseHotelDialog(buyDialog);
        handleBuyHouseWindowVisibility(buyDialog);
    }

    /*
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
    */

    public SellPlayerPropertyDialog getSellDialog() {
        return sellDialog;
    }

    public BuyHouseHotelDialog getBuyDialog() {
        return buyDialog;
    }
    // FIXME: end of dialog stuff

}



