import javax.swing.*;
import java.util.ArrayList;

public class PlayerPropertyListModel extends DefaultListModel<PrivateProperty> {
    MonopolyController controller;
    private JList<PrivateProperty> propertyList;

    public PlayerPropertyListModel(MonopolyController controller) {
        super();
        ArrayList<PrivateProperty> properties = controller.getCurrentPlayer().getPropertyList();
        //add(properties);

        for (PrivateProperty p : properties) {
            addElement(p);
        }

    }

    public void removeProperty(int index) {
        //Player p = controller.getCurrentPlayer();
        controller.sellProperty(index);
        removeElementAt(index);
    }
}
