import java.text.NumberFormat;
import java.util.Locale;

public class Product {

    private String id, name, desc;
    private double cost;

    public Product() {
        id = "";
        name = "";
        desc = "";
        cost = -1.0;
    }

    public Product(String id, String name, String desc, double price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.cost = price;
    }

    public void pad() {
        if (name.length() < 35) name = name.concat(" ".repeat(35 - name.length()));
        if (desc.length() < 75) desc = desc.concat(" ".repeat(75 - desc.length()));
        if (id.length() < 6) id = id.concat(" ".repeat(6 - id.length()));
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return desc;
    }

    public double getCost() {
        return cost;
    }

    public String getFormattedCost() {
        return NumberFormat.getCurrencyInstance(Locale.US).format(cost);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isComplete() {
        return !name.isBlank() && !desc.isBlank() && !id.isBlank() && cost >= 0;
    }
}
