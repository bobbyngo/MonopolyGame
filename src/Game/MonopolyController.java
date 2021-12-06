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

    private final MonopolyModel model;
    private final MonopolyGUIView view;
    /**
     * Game.MonopolyController constructor
     * @param model, MonopolyModel
     * @param view, MonopolyView
     */
    public MonopolyController(MonopolyModel model, MonopolyGUIView view) {
        this.model = model;
        this.view = view;
    }

    // FIXME: LEAVE THIS ALONE
    // MVC Example
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed");
        if(e.getSource() == view.getBuyBtn()){
            model.handleBuyBtn();
        }
        else if (e.getSource() == view.getEndTurnBtn()) {
            model.handleEndTurnBtn();
        }
        else if(e.getSource() == view.getPayTaxBtn()){
            model.handlePayTaxBtn();
        }

//        else if (e.getSource() == view.getShowStatsBtn()) {
//            handleShowStatsBtn();
//        }

        else if (e.getSource() == view.getRollBtn()) {

                model.handleRollDiceBtn();

        }
        else if(e.getSource() == view.getSellBtn()){
            model.handleSellBtn();
        }
        else if(e.getSource() == view.getBuyHouseBtn()){
            model.handleBuyHouseBtn();
        }
        else if(model.getBuyDialog() != null && e.getSource() == model.getBuyDialog().getBuyHouseBtn()){
                model.handleDialogBuyHouseBtn();
        }
        else if(model.getBuyDialog() != null && e.getSource() == model.getBuyDialog().getBuyHotelBtn()){
                model.handleDialogBuyHotelBtn();
        }
        else if (model.getSellDialog() != null && e.getSource() == model.getSellDialog().getSellBtn()) {
            model.handleDialogSellBtn();
        }
        else if (e.getSource() == view.getImportInternationalItem()) {
            String filename = JOptionPane.showInputDialog(view, "Import xml file for international names import:\n Ex: international.xml");
            if (filename != null) {
                model.importInternationalVersion(filename);
            }
        }
        else if (e.getSource() == view.getSaveItem()) {
            String filename = JOptionPane.showInputDialog(view, "Name the xml filename for game save: \n Ex: game1.xml");
            if (filename != null) {
                model.saveGameData(filename);
            }
        }
        else if (e.getSource() == view.getLoadItem()){
            String filename = JOptionPane.showInputDialog(view, "Load xml filename to resume the game:\n Ex: game1.xml");
            if (filename != null) {
                model.loadGameData(filename);
            }
        }
    }
    // FIXME: END OF LEAVING ALONE

}





