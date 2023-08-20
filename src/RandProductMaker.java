import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class RandProductMaker {

    static final String FILE = System.getProperty("user.dir") + "//random access file//products_raf.txt";
    static RandomAccessFile file;
    static ProductMakerFrame frame;
    static Product newProduct;

    public static void main(String[] args) {
        openFile();

        newProduct = new Product();
        frame = new ProductMakerFrame();

        // when the frame closes, the program closes the random access file before terminating
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                closeFile();
                ((JFrame) e.getComponent()).dispose();
            }
        });

        frame.setVisible(true);
    }

    public static void updateName(String s) {
        if (s == null) return;
        if (s.length() > 35) {
            JOptionPane.showMessageDialog(null, "Error: names cannot exceed 35 characters.",
                    "Error", JOptionPane.PLAIN_MESSAGE, null);
        } else {
            newProduct.setName(s);
        }
    }

    public static void updateDescription(String s) {
        if (s == null) return;
        if (s.length() > 75) {
            JOptionPane.showMessageDialog(null, "Error: descriptions cannot exceed 75 characters.",
                    "Error", JOptionPane.PLAIN_MESSAGE, null);
        } else {
            newProduct.setDescription(s);
        }
    }

    public static void updateID(String s) {
        if (s == null) return;
        if (s.length() > 6) {
            JOptionPane.showMessageDialog(null, "Error: IDs cannot exceed 6 characters.",
                    "Error", JOptionPane.PLAIN_MESSAGE, null);
        } else {
            newProduct.setId(s);
        }
    }

    public static String updatePrice(String s) {
        if (s == null || s.isBlank()) return "$0.00";
        if (s.charAt(0) == '$') s = s.substring(1);

        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        double newCost;
        try {
            newCost = nf.parse(s).doubleValue();
        } catch (ParseException e) {
            return newProduct.getCost() == -1.0 ?
                    "$0.00" :
                    newProduct.getFormattedCost();
        }

        newProduct.setCost(newCost);
        return newProduct.getFormattedCost();
    }

    /**
     * @return true if all fields of {{@link #newProduct}} hold real values, rather than placeholders
     */
    public static boolean productIsComplete() {
        updateName(frame.getNameField().getText());
        updateDescription(frame.getDescField().getText());
        updateID(frame.getIdField().getText());
        updatePrice(frame.getPriceField().getText());
        return newProduct != null && newProduct.isComplete();
    }

    public static void saveProduct() {
        try {
            newProduct.pad();
            file.writeUTF(newProduct.getId());
            file.writeUTF(newProduct.getName());
            file.writeUTF(newProduct.getDescription());
            file.writeDouble(newProduct.getCost());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newProduct = new Product();
    }

    private static void openFile() {
        Path path = Paths.get(FILE);
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
            file = new RandomAccessFile(FILE, "rw");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void closeFile() {
        try {
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
