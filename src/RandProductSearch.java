import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RandProductSearch {

    static final String FILE = System.getProperty("user.dir") + "//random access file//products_raf.txt";
    static List<Product> products;
    static ProductSearchFrame frame;

    public static void main(String[] args) {
        products = new ArrayList<>();
        readProducts();
        frame = new ProductSearchFrame();
        frame.setVisible(true);
    }

    static void readProducts() {
        try (RandomAccessFile raf = new RandomAccessFile
                (FILE, "r")) {
            long pointer = 0;
            while (pointer < raf.length()) {
                products.add(new Product(raf.readUTF(),raf.readUTF(),
                        raf.readUTF(), raf.readDouble()));
                pointer = raf.getFilePointer();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void search(String query) {
        try (Stream<Product> stream = products.stream().filter(p -> p.getName().toLowerCase()
                .contains(query.toLowerCase()))) {
            ArrayList<Product> matches = new ArrayList<>();
            stream.forEach(matches::add);
            frame.updateResults(matches);
        }
    }
}
