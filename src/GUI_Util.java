import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI_Util {

    static Font MED_PLAIN, MED_BOLD, MED_ITALIC, BIG_BOLD;
    static BufferedImage FRAME_ICON;
    static ImageIcon SEARCH, SEARCH_DISABLED;
    static int SCREEN_WIDTH, SCREEN_HEIGHT,
            WIDTH, HEIGHT;
    static Toolkit toolkit = Toolkit.getDefaultToolkit();

    public static void initGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        getFonts();
        getImages();
        calculateSizes();
    }

    public static Dimension getFrameSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    public static Point getFrameLocation() {
        return new Point((SCREEN_WIDTH - WIDTH) / 2, (SCREEN_HEIGHT - HEIGHT) / 3);
    }

    private static void calculateSizes() {
        Dimension screenSize = toolkit.getScreenSize();
        SCREEN_WIDTH = screenSize.width;
        SCREEN_HEIGHT = screenSize.height;
        WIDTH = SCREEN_WIDTH * 3 / 4;
        HEIGHT = SCREEN_HEIGHT * 3 / 4;
    }

    public static CompoundBorder getBorder(String title) {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder
                        (Color.BLACK, 3, true), title,
                TitledBorder.CENTER, TitledBorder.TOP);
        titledBorder.setTitleFont(GUI_Util.BIG_BOLD);
        return BorderFactory.createCompoundBorder
                (BorderFactory.createEmptyBorder(20, 20, 20, 20),
                        titledBorder);
    }

    public static JButton getButton(String text, ActionListener actionListener) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setFont(GUI_Util.MED_PLAIN);
        btn.addActionListener(actionListener);
        return btn;
    }

    private static void getFonts() {
        File dir = new File(System.getProperty("user.dir") + "//fonts//");
        try {
            Font reg = Font.createFont(Font.TRUETYPE_FONT, new File(dir +
                    "//Hack-Regular.ttf"));
            MED_PLAIN = reg.deriveFont(Font.PLAIN, 20f);
            Font bold = Font.createFont(Font.TRUETYPE_FONT, new File(dir +
                    "//Hack-Bold.ttf"));
            MED_BOLD = bold.deriveFont(Font.PLAIN, 20f);
            BIG_BOLD = bold.deriveFont(Font.PLAIN, 26f);
            Font it = Font.createFont(Font.TRUETYPE_FONT, new File(dir +
                    "//Hack-Italic.ttf"));
            MED_ITALIC = it.deriveFont(Font.PLAIN, 20f);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getImages() {
        String dir = System.getProperty("user.dir") + "//images//";
        try {
            FRAME_ICON = ImageIO.read(new File(dir + "//binary-code.png"));
            SEARCH = getSmoothIcon(dir + "//search.png");
            SEARCH_DISABLED = getSmoothIcon(dir + "//search_disabled.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ImageIcon getSmoothIcon(String file) {
        return new ImageIcon(toolkit.getImage(file)) {
            @Override
            public int getIconWidth() {
                return 20;
            }
            @Override
            public int getIconHeight() {
                return 20;
            }
            @Override
            public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
                g.drawImage(getImage(), x, y, 20, 20, null);
            }
        };
    }
}
