import javax.swing.*;
import java.awt.*;

public class ProductMakerFrame extends JFrame {

    GridBagConstraints gbc;
    JPanel centerPnl;
    JTextField nameField, descField, idField, priceField;
    JLabel productCount;
    JButton addBtn;

    public ProductMakerFrame() {
        super("Product Maker");
        GUI_Util.initGUI();
        setIconImage(GUI_Util.FRAME_ICON);
        setSize(GUI_Util.getFrameSize());
        setLocation(GUI_Util.getFrameLocation());
        getRootPane().setBorder(GUI_Util.getBorder("Product Maker"));

        // so the random access file has time to close before the program terminates
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        getContentPane().setLayout(new GridBagLayout());

        // creates a center panel that will hold the name/description/id/price JTextFields, as  well as
        // the button to add a product
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 5, 5);
        centerPnl = new JPanel(new GridBagLayout());
        centerPnl.setOpaque(false);

        addFieldLabel("Name", 0);
        gbc.insets = new Insets(0, 5, 5, 5);
        addFieldLabel("Description", 1);
        addFieldLabel("ID", 2);
        addFieldLabel("Price", 3);

        nameField = addField(0);
        descField = addField(1);
        idField = addField(2);
        priceField = addField(3);
        priceField.addActionListener(e -> priceField.setText(RandProductMaker.updatePrice(priceField.getText())));

        addEmptyVerticalPanel(0);
        addEmptyVerticalPanel(2);

        gbc.gridx = 1;
        gbc.gridy = gbc.ipadx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = gbc.weighty = 1;
        add(centerPnl, gbc);

        addBtn = GUI_Util.getButton("Add", e -> {
            // checks if all fields are filled out
            if (RandProductMaker.productIsComplete()) {

                // asks for confirmation
                if (JOptionPane.showConfirmDialog(null, "Add this product?", "Confirm",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null)
                        == JOptionPane.OK_OPTION) {
                    RandProductMaker.saveProduct(); // saves the product to random access file

                    // changes the product count on the screen
                    int spaceIndex = productCount.getText().lastIndexOf(' ');
                    int n = Integer.parseInt(productCount.getText().substring(spaceIndex + 1)) + 1;
                    productCount.setText("Total products added: " + n);

                    // clears fields
                    nameField.setText("");
                    descField.setText("");
                    idField.setText("");
                    priceField.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please be sure to fill out every field.",
                        "Product incomplete", JOptionPane.PLAIN_MESSAGE, null);
            }
        });

        // adds a bottom panel to hold the save button and the product count
        JPanel bottomPnl = new JPanel();

        productCount = new JLabel("Total products added: 0");
        productCount.setFont(GUI_Util.MED_ITALIC);
        productCount.setHorizontalTextPosition(SwingConstants.CENTER);

        bottomPnl.setOpaque(false);
        bottomPnl.setLayout(new BoxLayout(bottomPnl, BoxLayout.X_AXIS));
        bottomPnl.add(productCount);
        bottomPnl.add(Box.createHorizontalGlue());
        bottomPnl.add(addBtn);

        gbc.gridx = gbc.ipadx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weighty = 0;
        add(bottomPnl, gbc);
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getDescField() {
        return descField;
    }

    public JTextField getIdField() {
        return idField;
    }

    public JTextField getPriceField() {
        return priceField;
    }

    private void addFieldLabel(String text, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setOpaque(false);
        lbl.setFont(GUI_Util.MED_PLAIN);
        gbc.gridy = y;
        centerPnl.add(lbl, gbc);
    }

    private JTextField addField(int y) {
        JTextField tf = new JTextField();
        tf.setFont(GUI_Util.MED_PLAIN);
        gbc.gridx = 1;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1;
        centerPnl.add(tf, gbc);
        return tf;
    }

    private void addEmptyVerticalPanel(int x) {
        JPanel pnl = new JPanel();
        pnl.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.ipadx = WIDTH / 6;
        gbc.weightx = 0;
        gbc.weighty = 1;
        add(pnl, gbc);
    }
}
