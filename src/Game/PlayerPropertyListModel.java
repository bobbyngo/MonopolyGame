package Game;

import javax.swing.*;
import java.util.ArrayList;

public class PlayerPropertyListModel extends DefaultListModel<PrivateProperty> {
    private MonopolyModel model;

    public PlayerPropertyListModel(MonopolyModel model) {
        super();
        this.model= model;
        ArrayList<PrivateProperty> properties = model.getCurrentPlayer().getPropertyList();

        for (PrivateProperty p : properties) {
            addElement(p);
        }
    }

    public void removeProperty(int index) {
        removeElementAt(index);
    }
}
