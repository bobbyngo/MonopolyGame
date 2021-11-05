/*
 * Author: Patrick Liu
 * Student Number: 101142730
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;

public class MonopolyGUIView {
    private final JFrame frame;
    private final JPanel mainPanel;
    private final GridBagLayout gb;
    private final GridBagConstraints c;
    private final ArrayList<JPanel> squares;
    private final ArrayList<JLabel> playerLabels;

    public MonopolyGUIView(){
        frame = new JFrame("Monopoly Game");
        gb = new GridBagLayout();
        mainPanel = new JPanel(gb);
        c = new GridBagConstraints();
        squares = new ArrayList<>();
        playerLabels = new ArrayList<>();
    }

    public void SquaresLayout(){
        Board board = new Board();

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

    public void addSquareToBoard(){
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

    public void displayGUI(){
        SquaresLayout();
        addSquareToBoard();

        frame.add(mainPanel);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?")
                        == JOptionPane.OK_OPTION) {
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        MonopolyGUIView view = new MonopolyGUIView();
        view.displayGUI();
    }
}
