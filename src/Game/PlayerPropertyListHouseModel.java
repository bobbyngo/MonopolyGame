package Game;
import javax.swing.*;
import java.util.ArrayList;

public class PlayerPropertyListHouseModel extends DefaultListModel<PrivateProperty>{
    private MonopolyModel model;

    public PlayerPropertyListHouseModel(MonopolyModel model) {
        super();
        this.model = model;
        ArrayList<PrivateProperty> properties = this.model.getCurrentPlayer().getPropertyList();

        for (PrivateProperty p : properties) {
            addElement(p);
        }
    }
}
