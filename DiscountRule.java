import java.util.Date;
public interface DiscountRule {
    boolean isApplicable(int quantity, Date currentDate);
    double applyDiscount(double totalPrice);
}

   