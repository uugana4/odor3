import java.text.SimpleDateFormat;
import java.util.Date;

public class DiscountRule {
    private double percentage;
    private Date startDate, endDate;

    public DiscountRule(double percentage, Date startDate, Date endDate) {
        this.percentage = percentage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isApplicable(int quantity, Date currentDate) {
        return !currentDate.before(startDate) && !currentDate.after(endDate);
    }

    public double applyDiscount(double originalPrice) {
        return originalPrice * (1 - percentage / 100);
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return String.format("%.2f%% (%s - %s)", percentage, sdf.format(startDate), sdf.format(endDate));
    }
}
