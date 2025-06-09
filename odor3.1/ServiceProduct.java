import java.util.Date;

public class ServiceProduct extends Product {
    private String serviceDetails;

    public ServiceProduct(String name, double price, String serviceDetails) {
        super(name, price, 0); 
        this.serviceDetails = serviceDetails;
    }

    public String getServiceDetails() { return serviceDetails; }
    public void setServiceDetails(String serviceDetails) { this.serviceDetails = serviceDetails; }

    @Override
    public double getFinalPrice(int quantity, Date currentDate) {
      
        return getPrice();
    }
}
