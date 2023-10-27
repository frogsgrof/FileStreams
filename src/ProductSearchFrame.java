import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ProductSearchFrame extends JFrame {

    JPanel searchPnl, resultsPnl;
    static final String SEARCH = "SEARCH",
            RESULTS = "RESULTS";
    JLabel searchLbl;
    JTextField searchField;
    String query;
    JTabbedPane productTabs;

    public ProductSearchFrame() {
        super("Product Search");
        GUI_Util.initGUI();
        setIconImage(GUI_Util.FRAME_ICON);
        setSize(GUI_Util.getFrameSize());
        setLocation(GUI_Util.getFrameLocation());
        getRootPane().setBorder(GUI_Util.getBorder("Product Search"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new CardLayout());
        addSearchPanel();
        addResultsPanel();
    }

    public void updateResults(ArrayList<Product> results) {
        TitledBorder border = (TitledBorder) productTabs.getBorder();
        border.setTitle(border.getTitle() + '\"' + query + "\":");
        productTabs.setBorder(border);

        for (Product p : results) {
            JPanel tab = new JPanel(new GridBagLayout());
            tab.setOpaque(false);

            JLabel nameLbl = getLabel("Name: "),
                    descLbl = getLabel("Description: "),
                    idLbl = getLabel("ID: "),
                    costLbl = getLabel("Cost: "),
                    tabLbl = getLabel(p.getName().length() > 12 ?
                            p.getName().substring(0, 12).concat("...") :
                            p.getName());
            JTextArea name = getTextArea(p.getName()),
                    desc = getTextArea(p.getDescription()),
                    id = getTextArea(p.getId()),
                    cost = getTextArea(p.getFormattedCost());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.insets = new Insets(5, 5, 5, 5);
            tab.add(nameLbl, gbc);
            gbc.gridy = 1;
            tab.add(idLbl, gbc);
            gbc.gridy = 2;
            tab.add(descLbl, gbc);
            gbc.gridy = 3;
            tab.add(costLbl, gbc);
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.BOTH;
            tab.add(name, gbc);
            gbc.gridy = 1;
            tab.add(id, gbc);
            gbc.gridy = 2;
            tab.add(desc, gbc);
            gbc.gridy = 3;
            gbc.weighty = 1;
            tab.add(cost, gbc);

            JScrollPane tabScroll = new JScrollPane(tab);
            tabScroll.setOpaque(false);
            tabScroll.getViewport().setOpaque(false);
            tabScroll.setBorder(null);
            tabScroll.getViewport().setBorder(null);
            tabScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
            tabScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            tabScroll.getVerticalScrollBar().setUnitIncrement(16);

            productTabs.add(tabScroll);
            productTabs.setTabComponentAt(productTabs.getTabCount() - 1, tabLbl);
        }
        revalidate();
    }

    private void addSearchPanel() {
        searchPnl = new JPanel(new GridBagLayout());
        searchPnl.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        searchLbl = new JLabel(GUI_Util.SEARCH);
        searchLbl.setDisabledIcon(GUI_Util.SEARCH_DISABLED);
        searchLbl.setEnabled(false);
        searchLbl.setOpaque(false);
        gbc.gridx = gbc.gridy = 1;
        gbc.weighty = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        searchPnl.add(searchLbl, gbc);
        gbc.insets = new Insets(0, 0, 0, 0);

        searchField = new JTextField();
        searchField.setOpaque(false);
        searchField.setFont(GUI_Util.MED_PLAIN);
        gbc.gridx = 2;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        searchPnl.add(searchField, gbc);

        JButton searchBtn = GUI_Util.getButton("Search", e -> {
            if (query != null) {
                RandProductSearch.search(query);
                ((CardLayout) getContentPane().getLayout()).show(getContentPane(), RESULTS);
                revalidate();
            }
        });
        searchBtn.setEnabled(false);
        searchBtn.setMnemonic(KeyEvent.VK_ENTER);

        searchField.addActionListener(e -> {
            query = searchField.getText();
            searchLbl.setEnabled(true);
            searchBtn.setEnabled(true);
            searchBtn.requestFocusInWindow();
        });

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHEAST;
        searchPnl.add(searchBtn, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weighty = 1;
        searchPnl.add(new JPanel(), gbc);
        gbc.gridy = 3;
        searchPnl.add(new JPanel(), gbc);

        addEmptyVerticalPanel(searchPnl, 0);
        addEmptyVerticalPanel(searchPnl, 3);

        add(searchPnl, SEARCH);
    }

    private void addResultsPanel() {
        resultsPnl = new JPanel(new GridBagLayout());
        resultsPnl.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        productTabs = new JTabbedPane();
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder
                (40, 40, 40, 40), "Product names containing ",
                TitledBorder.LEFT, TitledBorder.BELOW_TOP);
        border.setTitleFont(GUI_Util.MED_BOLD);
        productTabs.setBorder(border);

        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        resultsPnl.add(productTabs, gbc);

        gbc.weighty = 0;
        gbc.gridy = 1;
        resultsPnl.add(new JPanel(), gbc);

        add(resultsPnl, RESULTS);
    }

    private void addEmptyVerticalPanel(Container container, int x) {
        JPanel pnl = new JPanel();
        pnl.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        gbc.weightx = 0.5;
        gbc.weighty = 1;
        container.add(pnl, gbc);
    }

    private static JLabel getLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setOpaque(false);
        lbl.setFont(GUI_Util.MED_PLAIN);
        return lbl;
    }

    private static JTextArea getTextArea(String text) {
        JTextArea ta = new JTextArea(text);
        ta.setOpaque(false);
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setFont(GUI_Util.MED_PLAIN);
        JScrollPane sp = new JScrollPane(ta);
        sp.setBorder(null);
        sp.getViewport().setBorder(null);
        sp.setOpaque(false);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        return ta;
    }
}
