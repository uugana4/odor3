import java.util.Date;

public class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getTotal(Date date) {
        return product.getFinalPrice(quantity, date) * quantity;
    }

    public void confirmSale() { product.sell(quantity); }

    public void print(Date date) {
        System.out.printf("%-15s x%-3d = %,10.2f₮\n", product.getName(), quantity, getTotal(date));
    }
}
