package Game;
import javax.swing.*;
import java.awt.*;

public class BuyHouseHotelDialog extends JDialog {
    private MonopolyController controller;
    //private MonopolyModel model;
    private PlayerPropertyListHouseModel propertyListModel;
    private JList<PrivateProperty> list;
    private JButton buyHouseBtn;
    private JButton buyHotelBtn;
    private JButton closeBtn;

    public BuyHouseHotelDialog(JFrame owner, MonopolyModel model, MonopolyController controller) {
        super(owner, "Buy Houses or Hotels", true);
        this.controller = controller;
        Player player = model.getCurrentPlayer();
        propertyListModel = new PlayerPropertyListHouseModel(model);
        model.retrieveBuyPanelModel(this, propertyListModel);
        list = new JList<>(propertyListModel);

        this.setLayout(new BorderLayout());

        this.setPreferredSize(new Dimension(400, 250));
        pack();

        setLocationRelativeTo(null);

        JScrollPane scrollPane = new JScrollPane(list);
        this.add(scrollPane, BorderLayout.CENTER);

        this.add(new JLabel(String.format("%s is buying houses/hotel, double click to select",
                player.getName())), BorderLayout.PAGE_START);

        JPanel btnPanel = new JPanel();
        add(btnPanel, BorderLayout.PAGE_END);
        closeBtn = new JButton("Close");
        buyHouseBtn = new JButton("Buy House");
        buyHotelBtn = new JButton("Buy Hotel");
        closeBtn.addActionListener(e->dispose());
        buyHouseBtn.addActionListener(controller);
        buyHotelBtn.addActionListener(controller);

        btnPanel.add(buyHouseBtn);
        btnPanel.add(buyHotelBtn);
        btnPanel.add(closeBtn);
    }

    public JButton getBuyHouseBtn(){
        return buyHouseBtn;
    }

    public JButton getBuyHotelBtn(){
        return buyHotelBtn;
    }

    public JList<PrivateProperty> getList(){
        return list;
    }
}
