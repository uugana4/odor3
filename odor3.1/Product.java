import java.util.Date;

public class Product extends AbstractProduct {
    private int totalStock;
    private int soldQuantity = 0;
    private DiscountRule discountRule;

    public Product(String name, double price, int totalStock) {
        super(name, price);
        this.totalStock = totalStock;
    }

    public int getRemainingStock() {
        return totalStock - soldQuantity;
    }

    @Override
    public double getFinalPrice(int quantity, Date currentDate) {
        if (discountRule != null && discountRule.isApplicable(quantity, currentDate)) {
            return discountRule.applyDiscount(getPrice());
        }
        return getPrice();
    }

    public void sell(int quantity) {
        if (quantity <= getRemainingStock()) {
            soldQuantity += quantity;
        } else {
            System.out.println("Алдаа: үлдэгдэл хүрэлцэхгүй байна!");
        }
    }

  
    public void setTotalStock(int newStock) {
        if (newStock >= soldQuantity) {
            this.totalStock = newStock;
        } else {
            System.out.println("Үлдэгдэл тоо зарсан тооноос бага байж болохгүй!");
        }
    }

    public void setDiscountRule(DiscountRule rule) {
        this.discountRule = rule;
    }

    public DiscountRule getDiscountRule() {
        return discountRule;
    }
}
