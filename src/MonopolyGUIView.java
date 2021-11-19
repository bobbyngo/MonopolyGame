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
import java.util.Map;

public class MonopolyGUIView extends JFrame {
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
        this.showStatsBtn.addActionListener(controller);

        //MVC Example
        this.buyBtn = new JButton();
        this.buyBtn.addActionListener(controller);

        sellBtn = new JButton();
        sellBtn.addActionListener(controller);

        this.endTurnBtn = new JButton();
        this.endTurnBtn.addActionListener(controller);

        this.payTaxBtn = new JButton();
        this.payTaxBtn.addActionListener(controller);

        // Dice Initialization
        this.rollBtn = new JButton();

        // Add rollBtn ActionListener to this class
        this.rollBtn.addActionListener(controller);
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
     * Method handles roll dice button. It will delete the 2 labels next to the
     * Roll Dice button and update the 2 new die face images corresponding to the
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
        for (int i = 0; i < controller.getDie().getNUM_DICE(); i ++) {
            try {
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
            }catch (Exception e){
                System.out.println("Image related with dice face " + i + " not found");
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
    public JButton getShowStatsBtn() {
        return showStatsBtn;

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
        }else if(dialogNum == 16){
            rollBtn.setEnabled(false);
        }else if(dialogNum == 17){
            playerLabels.get(player.getCurrLocation().getIndex()).setText("");
        }else if(dialogNum == 18){

            StringBuilder str = new StringBuilder();
            for (Player playa : player.getCurrLocation().getPlayersCurrentlyOn()) {
                str.append("%s\n");
            }

            playerLabels.get(player.getCurrLocation().getIndex()).setText(String.valueOf(str));
            playerLabels.get(player.getCurrLocation().getIndex()).setText(player.getName());
        }else if(dialogNum == 19){
            JOptionPane.showMessageDialog(null, String.format("%s cannot afford this fee.\n Bankrupt!", player.getName()) +
                    "Game is Over");
        }else if(dialogNum == 20){
            JOptionPane.showMessageDialog(null, String.format("%s is the winner with total value of $%d",
                    player.getName(), player.getPlayerTotalAsset()));
            this.dispose();
        }else if(dialogNum == 21){
            textLabel.setText(String.format("<html> %s's turn <br> Location: %s <br> Owner: %s", player.getName(), player.getCurrLocation().getName(), ((PrivateProperty) player.getCurrLocation()).getOwner().getName()));
        }else if(dialogNum == 22){
            textLabel.setText(String.format("<html> %s's turn <br> Location: %s", player.getName(), player.getCurrLocation().getName()));
        }else if(dialogNum == 23){
            JOptionPane.showMessageDialog(null, String.format("%s has rolled a DOUBLE!", player.getName()));
        }else if(dialogNum == 24){
            JOptionPane.showMessageDialog(null, String.format("%s has been caught SPEEDING!", player.getName()) +
                    "They have been sent to jail and their turn shall be skipped for 3 rounds.");
        }else if(dialogNum == 25){
            JOptionPane.showMessageDialog(null, String.format("%s is on Go To Jail. Turn Ended.", player.getName()));
        }
    }

    public void handleDiceViewUpdate(int roll1, int roll2){
        updateDiceFaces(roll1, roll2);
    }

    public void handleSellWindowVisibility(SellPlayerPropertyDialog window){
        window.setVisible(true);
    }

    public static void main(String[] args) {
        MonopolyGUIView view = new MonopolyGUIView();
        view.displayGUI();
    }

}



