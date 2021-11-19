import javax.swing.*;
import java.util.ArrayList;

public class PlayerPropertyListModel extends DefaultListModel<PrivateProperty> {
    MonopolyController controller;

    public PlayerPropertyListModel(MonopolyController controller) {
        super();
        this.controller = controller;
        ArrayList<PrivateProperty> properties = controller.getCurrentPlayer().getPropertyList();

        for (PrivateProperty p : properties) {
            addElement(p);
        }
    }

    public void removeProperty(int index) {
        removeElementAt(index);
    }
}
