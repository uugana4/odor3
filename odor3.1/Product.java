import java.util.Date;

public class Product {
    private String name;
    private double price;
    private int totalStock;
    private int soldQuantity = 0;
    private DiscountRule discountRule;

    public Product(String name, double price, int totalStock) {
        this.name = name;
        this.price = price;
        this.totalStock = totalStock;
    }

    public void setName(String newName) { this.name = newName; }
    public void setPrice(double newPrice) { this.price = newPrice; }
    public void setTotalStock(int newStock) {
        if (newStock >= soldQuantity) {
            this.totalStock = newStock;
        } else {
            System.out.println("Үлдэгдэл тоо зарсан тооноос бага байж болохгүй!");
        }
    }
    public void setDiscountRule(DiscountRule rule) { this.discountRule = rule; }
    public int getRemainingStock() { return totalStock - soldQuantity; }
    public double getFinalPrice(int quantity, Date currentDate) {
        if (discountRule != null && discountRule.isApplicable(quantity, currentDate)) {
            return discountRule.applyDiscount(price);
        }
        return price;
    }
    public void sell(int quantity) {
        if (quantity <= getRemainingStock()) {
            soldQuantity += quantity;
        } else {
            System.out.println("Алдаа: үлдэгдэл хүрэлцэхгүй байна!");
        }
    }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public DiscountRule getDiscountRule() { return discountRule; }
}
