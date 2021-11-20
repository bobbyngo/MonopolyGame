package Game;
import javax.swing.*;
import java.awt.*;

public class SellPlayerPropertyDialog extends JDialog {
    private MonopolyController controller;
    private PlayerPropertyListModel PropertyListModel;
    private JList<PrivateProperty> list;
    private JButton closeBtn;
    private JButton sellBtn;

    public SellPlayerPropertyDialog(JFrame owner, MonopolyController controller) {
        super(owner, "Sell Player Property", true);
        this.controller = controller;
        Player player = controller.getCurrentPlayer();
        PropertyListModel = new PlayerPropertyListModel(controller);
        controller.retrieveSellPanelModel(this, PropertyListModel);
        list = new JList<>(PropertyListModel);

        this.setLayout(new BorderLayout());

        // Fixed the size dialog for you zak : )
        this.setPreferredSize(new Dimension(250, 250));
        pack();

        // Bonus: Make the dialog in the middle
        setLocationRelativeTo(null);


        JScrollPane scrollPane = new JScrollPane(list);
        this.add(scrollPane, BorderLayout.CENTER);

        this.add(new JLabel(String.format("Sell %s's Properties. Double-click to sell.",
                player.getName())), BorderLayout.PAGE_START);

        JPanel btnPanel = new JPanel();
        add(btnPanel, BorderLayout.PAGE_END);
        closeBtn = new JButton("Close");
        sellBtn = new JButton("Sell");
        closeBtn.addActionListener(e->dispose());
        sellBtn.addActionListener(controller);

        btnPanel.add(sellBtn);
        btnPanel.add(closeBtn);
    }

    public JButton getSellBtn(){
        return sellBtn;
    }

    public JList<PrivateProperty> getList(){
        return list;
    }
}
