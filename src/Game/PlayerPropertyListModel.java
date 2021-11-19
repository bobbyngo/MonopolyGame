package Game;

import Game.MonopolyController;

import javax.swing.*;
import java.util.ArrayList;

public class PlayerPropertyListModel extends DefaultListModel<PrivateProperty> {
    MonopolyController controller;
    private JList<PrivateProperty> propertyList;

    public PlayerPropertyListModel(MonopolyController controller) {
        super();
        this.controller = controller;
        ArrayList<PrivateProperty> properties = controller.getCurrentPlayer().getPropertyList();
        //add(properties);

        for (PrivateProperty p : properties) {
            addElement(p);
        }

    }

    public void removeProperty(int index) {
        //Game.Player p = controller.getCurrentPlayer();
        controller.sellProperty(index);
        removeElementAt(index);
    }
}
