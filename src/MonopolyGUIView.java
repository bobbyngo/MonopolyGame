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

public class MonopolyGUIView extends JFrame{
    private Board board;
    private final JPanel mainPanel;
    private final GridBagLayout gb;
    private final GridBagConstraints c;
    private final ArrayList<JPanel> squares;
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


    private MonopolyController controller;

    public MonopolyGUIView(){
        board = new Board();
        gb = new GridBagLayout();
        mainPanel = new JPanel(gb);
        c = new GridBagConstraints();
        squares = new ArrayList<>();
        playerLabels = new ArrayList<>();

        showStats = new JButton();
        rollBtn = new JButton();
        buy = new JButton();
        sell = new JButton();
        endTurn = new JButton();
        payTax = new JButton();

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
        rollBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                roll = controller.rollDie();
                System.out.println(String.format("die 1: %d, die 2: %d", roll[0], roll[1]));


            }
        });
    }

    private void addButtonToBoard(){
        // Show Stats button
        c.gridx = 2;
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
        c.gridx = 4;
        gb.setConstraints(rollBtn, c);
        rollBtn.setText("Roll Dice");
        rollBtn.setForeground(Color.RED);
        mainPanel.add(rollBtn);

        // Buy Button
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

}
