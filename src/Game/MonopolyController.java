package Game;

import Game.Bank;
import Game.BankProperty;
import Game.Board;
import Game.Business;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Gabriel Benni Kelley Evensen 101119814
 *
 * The controller for the game of monopoly; handles the players, board, banks, DIE, and the current player
 */
public class MonopolyController implements ActionListener {

    /**
     * Game.MonopolyController constructor
     *
     * @param players, an ArrayList of Game.Player's that are in the game
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
        SellDialog = new SellPlayerPropertyDialog(this.view, this);
        BuyDialog = new BuyHouseHotelDialog(this.view, this);

        for(Player p: this.players){
            p.setCurrLocation(board.getSQUARE(0));
        }
    }

    // FIXME: LEAVE THIS ALONE
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

//        else if (e.getSource() == view.getShowStatsBtn()) {
//            handleShowStatsBtn();
//        }

        else if (e.getSource() == view.getRollBtn()) {

                handleRollDiceBtn();

        }
        else if(e.getSource() == view.getSellBtn()){
            handleSellBtn();
        }
        else if(e.getSource() == view.getBuyHouseBtn()){
            handleBuyHouseBtn();
        }
        else if(e.getSource() == BuyDialog.getBuyHouseBtn()){
                handleDialogBuyHouseBtn();
        }
        else if(e.getSource() == BuyDialog.getBuyHotelBtn()){
                handleDialogBuyHotelBtn();
        }
        else if (e.getSource() == SellDialog.getSellBtn()) {
            handleDialogSellBtn();
        }
    }
    // FIXME: END OF LEAVING ALONE

}





