/*
 * Author: Patrick Liu
 * Student Number: 101142730
 */

import javax.swing.*;
import java.awt.*;
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
    private final JButton roll;
    private final JButton buy;
    private final JButton sell;
    private final JButton endTurn;


    public MonopolyGUIView(){
        board = new Board();
        gb = new GridBagLayout();
        mainPanel = new JPanel(gb);
        c = new GridBagConstraints();
        squares = new ArrayList<>();
        playerLabels = new ArrayList<>();

        showStats = new JButton();
        roll = new JButton();
        buy = new JButton();
        sell = new JButton();
        endTurn = new JButton();
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
        gb.setConstraints(roll, c);
        roll.setText("Roll Dice");
        roll.setForeground(Color.RED);
        // content of the action listener will be replaced with a function in Monopoly Controller to display the current player stats
        roll.addActionListener(e->System.out.println("hello"));
        mainPanel.add(roll);
    }

    public void displayGUI(){
        SquaresLayout();
        addSquareToBoard();
        addButtonToBoard();

        add(mainPanel);
        pack();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        MonopolyGUIView self = this;
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (JOptionPane.showConfirmDialog(self, "Are you sure you want to quit?")
                        == JOptionPane.OK_OPTION) {
                    self.setVisible(false);
                    self.dispose();
                }
            }
        });

        self.setVisible(true);
    }

    public static void main(String[] args) {
        MonopolyGUIView view = new MonopolyGUIView();
        view.displayGUI();
    }
}