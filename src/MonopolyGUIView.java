/*
 * Author: Patrick Liu
 * Student Number: 101142730
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
    private final JPanel textPanel;
    private final JLabel textLabel;
    private final ArrayList<JLabel> playerLabels;

    private final JButton showStatsBtn;
    private final JButton rollBtn;
    private final JButton buyBtn;
    private final JButton sell;
    private final JButton endTurnBtn;
    private final JButton payTaxBtn;

    //For Roll Dice
    int[] roll;
    private JLabel diceLabel1;
    private JLabel diceLabel2;
    private ImageIcon dieFace;


    private MonopolyController controller;

    public MonopolyGUIView(){
        board = new Board();
        gb = new GridBagLayout();
        mainPanel = new JPanel(gb);
        c = new GridBagConstraints();
        squares = new ArrayList<>();
        textPanel = new JPanel();
        textLabel = new JLabel();
        playerLabels = new ArrayList<>();

        this.showStatsBtn = new JButton();
        this.showStatsBtn.addActionListener(this);

        this.buyBtn = new JButton();
        this.buyBtn.addActionListener(this);

        sell = new JButton();

        this.endTurnBtn = new JButton();
        this.endTurnBtn.addActionListener(this);

        this.payTaxBtn = new JButton();
        this.endTurnBtn.addActionListener(this);

        // Dice Initialization
        this.rollBtn = new JButton();
        // Add rollBtn ActionListener to this class
        this.rollBtn.addActionListener(this);
        this.diceLabel1 = new JLabel();
        this.diceLabel2 = new JLabel();

        ArrayList<Player> players = new ArrayList<>();
        //For running the code, players array list cannot be empty
        players.add(new Player("a", null));
        controller = new MonopolyController(players);
    }

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

            // This line is just for testing playerLabel
            playerLabel.setText("player 1");

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

    private void handleShowStatsBtn() {
        //TODO
    }

    private void handleBuyPropertyBtn() {
        //TODO
    }

    private void handlePayTaxBtn() {
        //TODO
    }

    private void handleEndTurnBtn() {
        //TODO
    }

    private void updatePlayerLocation() {
        //TODO
    }

    /**
     * Method handles roll dice button. It will delete the 2 labels next to the
     * Roll Dice button and update the 2 new die face images corresponding to the
     * value that player rolls the dice
     * @throws IOException
     */
    private void handleRollDiceBtn() throws IOException {
        // Calling the rollDie function
        roll = controller.rollDie();

        // Update the new label when the button is clicked
        // Remove 2 labels if available
        mainPanel.remove(diceLabel1);
        mainPanel.remove(diceLabel2);

        // Stinky code but it works I will refactor later
        JLabel dieLabel = null;
        for (int i = 0; i < controller.getDie().getSIZE(); i ++) {
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

        mainPanel.validate();
        mainPanel.repaint();

        // For debugging
        System.out.println(String.format("die 1: %d, die 2: %d", roll[0], roll[1]));
    }

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
        showStatsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JOptionPane.showMessageDialog(null,
                            "<html><u>Properties</u>:- \n" + "\t" + controller.getCurrentPlayer().propertiesToString() + "\n" +
                            "<html><u>Liquid value</u>:- $" + controller.getCurrentPlayer().getPlayerBalance() + "\n" +
                            "<html><u>Total value (property prices included)</u>:- $" + controller.getCurrentPlayer().getPlayerTotalAsset(), "Player " + controller.getCurrentPlayer().getName() + "'s stats", JOptionPane.INFORMATION_MESSAGE);

                textLabel.setText(String.format("<html><u>Properties</u>:- \n" + "\t" + controller.getCurrentPlayer().propertiesToString() + "\n" +
                        "<html><u>Liquid value</u>:- $" + controller.getCurrentPlayer().getPlayerBalance() + "\n" +
                        "<html><u>Total value (property prices included)</u>:- $" +
                        controller.getCurrentPlayer().getPlayerTotalAsset(), "Player " + controller.getCurrentPlayer().getName()+ "'s stats"));
            }
        });
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
        gb.setConstraints(sell, c);
        sell.setText("Sell Property");
        sell.setForeground(Color.RED);
        mainPanel.add(sell);

        // payTax Button
        c.gridy = 5;
        gb.setConstraints(payTaxBtn, c);
        payTaxBtn.setText("Pay Tax");
        payTaxBtn.setForeground(Color.RED);
        mainPanel.add(payTaxBtn);

        // endTurn Button
        c.gridy = 6;
        gb.setConstraints(endTurnBtn, c);
        endTurnBtn.setText("End Turn");
        endTurnBtn.setForeground(Color.RED);
        mainPanel.add(endTurnBtn);
    }

    public void displayGUI(){
        this.SquaresLayout();
        this.addSquareToBoard();

        this.addButtonToBoard();

        this.add(mainPanel);
        this.pack();

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

    public static void main(String[] args) {
        MonopolyGUIView view = new MonopolyGUIView();
        view.displayGUI();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == showStatsBtn) {
            handleShowStatsBtn();
        }

        else if (e.getSource() == rollBtn) {
            try {
                handleRollDiceBtn();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        else if (e.getSource() == buyBtn) {
            handleBuyPropertyBtn();
        }

        else if (e.getSource() == payTaxBtn) {
            handlePayTaxBtn();
        }
        else if (e.getSource() == endTurnBtn) {
            handleEndTurnBtn();
        }
    }
}



