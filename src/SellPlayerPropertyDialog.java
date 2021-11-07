import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SellPlayerPropertyDialog extends JDialog {
    private MonopolyController controller;
    private PlayerPropertyListModel propertyListModel;
    private JList<PrivateProperty> list;
    private JButton closeBtn;
    private JButton sellBtn;

    public SellPlayerPropertyDialog(JFrame owner, MonopolyController controller) {
        super(owner, "Sell Player Property", true);
        this.controller = controller;
        Player player = controller.getCurrentPlayer();
        propertyListModel = new PlayerPropertyListModel(controller);
        list = new JList<>(propertyListModel);
        //list = new JList<>(player.getPropertyList().toArray(new PrivateProperty[0]));
        //propertyListModel = new PlayerPropertyListModel(controller);

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
        sellBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = list.getSelectedIndex();
                if (index != -1) {
                    propertyListModel.removeProperty(index);
                }
            }
        });

        btnPanel.add(sellBtn);
        btnPanel.add(closeBtn);



    }
}
