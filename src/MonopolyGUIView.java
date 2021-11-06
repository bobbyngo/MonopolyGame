/*
 * Author: Patrick Liu
 * Student Number: 101142730
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

    private final JButton showStats;
    private final JButton rollBtn;
    private final JButton buy;
    private final JButton sell;
    private final JButton endTurn;
    private final JButton payTax;

    //For dice roll
    int[] roll;
    private JPanel dicePanel;
    private JLabel diceLabel;


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

        showStats = new JButton();
        buy = new JButton();
        sell = new JButton();
        endTurn = new JButton();
        payTax = new JButton();

        // Dice Initialization
        this.rollBtn = new JButton();
        // Add rollBtn ActionListener to this class
        this.rollBtn.addActionListener(this);
        this.diceLabel = new JLabel();
        this.dicePanel = new JPanel();

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

    private void addRollBtn() {
        // Calling the rollDie function
        roll = controller.rollDie();

        // Remove the old dice label next to the roll dice btn
        mainPanel.remove(diceLabel);
        JLabel newLabel = new JLabel("Btn is pressed");

        mainPanel.add(newLabel);
        //this.add(mainPanel);
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
        //Example
        textLabel.setText("<Html> In my great grandmother's time<br>All one needed was a broom<br>To get to see places<br>And give the geese a chase in the sky.<html>");
        textPanel.add(textLabel);
        mainPanel.add(textPanel);

        // Show Stats button
        c.gridy = 2;
        c.gridwidth = 4;
        c.gridheight = 2;
        gb.setConstraints(showStats, c);
        showStats.setText("Show Stats");
        showStats.setForeground(Color.RED);
        // content of the action listener will be replaced with a function in Monopoly Controller to display the current player stats
        showStats.addActionListener(e->System.out.println("hello"));
        mainPanel.add(showStats);

        // Roll Button
        c.gridx = 3;
        gb.setConstraints(rollBtn, c);
        rollBtn.setText("Roll Dice");
        rollBtn.setForeground(Color.RED);
        mainPanel.add(rollBtn);

        // Dice Label
        c. gridx = 5;
        diceLabel.setText("Click Roll Dice to see the magic");
        gb.setConstraints(diceLabel, c);
        mainPanel.add(diceLabel);

        // Buy Button
        c.gridx = 3;
        c.gridy = 3;
        gb.setConstraints(buy, c);
        buy.setText("Buy Property");
        buy.setForeground(Color.RED);
        // content of the action listener will be replaced with a function in Monopoly Controller to display the current player stats
        buy.addActionListener(e->System.out.println("hello"));
        mainPanel.add(buy);

        // Sell Button
        c.gridy = 4;
        gb.setConstraints(sell, c);
        sell.setText("Sell Property");
        sell.setForeground(Color.RED);
        // content of the action listener will be replaced with a function in Monopoly Controller to display the current player stats
        sell.addActionListener(e->System.out.println("hello"));
        mainPanel.add(sell);

        // payTax Button
        c.gridy = 5;
        gb.setConstraints(payTax, c);
        payTax.setText("Pay Tax");
        payTax.setForeground(Color.RED);
        // content of the action listener will be replaced with a function in Monopoly Controller to display the current player stats
        payTax.addActionListener(e->System.out.println("hello"));
        mainPanel.add(payTax);

        // endTurn Button
        c.gridy = 6;
        gb.setConstraints(endTurn, c);
        endTurn.setText("End Turn");
        endTurn.setForeground(Color.RED);
        // content of the action listener will be replaced with a function in Monopoly Controller to display the current player stats
        endTurn.addActionListener(e->System.out.println("hello"));
        mainPanel.add(endTurn);
    }

    public void displayGUI(){
        this.SquaresLayout();
        this.addSquareToBoard();
        this.addRollBtn();
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
        System.out.println(e.getActionCommand());
        if (e.getActionCommand().equals("Roll Dice")) {
            System.out.println("Zak is carrying");
        }
    }
}



