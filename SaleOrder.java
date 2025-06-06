import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SaleOrder {
    private List<OrderItem> items = new ArrayList<>();
    private Date date;

    public SaleOrder() { this.date = new Date(); }

    public void addItem(OrderItem item) {
        items.add(item);
        item.confirmSale();
    }

    public void printInvoice() {
        if (items.isEmpty()) {
            System.out.println("\nЗахиалга хоосон байна.");
            return;
        }
        double total = 0;
        System.out.println("\n--- Нэхэмжлэл ---");
        for (OrderItem item : items) {
            item.print(date);
            total += item.getTotal(date);
        }
        System.out.printf("Нийт дүн: %,10.2f₮\n", total);
    }
}