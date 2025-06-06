import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    void testProductCreation() {
        Product product = new Product("Coffee", 10.0, 5);
        assertEquals("Coffee", product.getName());
        assertEquals(10.0, product.getPrice());
        assertEquals(5, product.getStockQuantity());
    }

    @Test
    void testProductEquality() {
        Product p1 = new Product("Tea", 8.0, 3);
        Product p2 = new Product("Tea", 8.0, 3);
        assertEquals(p1, p2, "Products with same name should be equal");
    }
}
