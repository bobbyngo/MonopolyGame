package Game;
import javax.swing.*;
import java.util.ArrayList;

public class PlayerPropertyListHouseModel extends DefaultListModel<PrivateProperty>{
    MonopolyController controller;

    public PlayerPropertyListHouseModel(MonopolyController controller) {
        super();
        this.controller = controller;
        ArrayList<PrivateProperty> properties = controller.getCurrentPlayer().getPropertyList();

        for (PrivateProperty p : properties) {
            addElement(p);
        }
    }
}
