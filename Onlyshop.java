import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;

// User Role enum
enum Role {
    ADMIN, USER
}

// User class
class User {
    private String username;
    private String password;
    private Role role;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public boolean checkPassword(String pw) { return password.equals(pw); }
    public Role getRole() { return role; }
}

// Product class
class Product {
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

// DiscountRule class
class DiscountRule {
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

// OrderItem class
class OrderItem {
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

// SaleOrder class
class SaleOrder {
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

// Main class
public class Onlyshop{
    static Scanner sc = new Scanner(System.in);
    static List<User> users = new ArrayList<>();
    static List<Product> catalog = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        // Урьдчилан хэрэглэгч үүсгэх
        users.add(new User("admin", "admin123", Role.ADMIN));
        users.add(new User("user1", "user123", Role.USER));
        users.add(new User("user2", "user234", Role.USER));

        while (true) {
            User currentUser = null;
            while (currentUser == null) {
                System.out.println("=== Та юу хийх вэ? ===");
                System.out.println("1. Нэвтрэх (Login)");
                System.out.println("2. Бүртгүүлэх (Sign Up)");
                System.out.print("Сонголт: ");
                String choice = sc.nextLine();
                if (choice.equals("1")) {
                    currentUser = login();
                    if (currentUser == null) {
                        System.out.println("Нэвтрэлт амжилтгүй боллоо.");
                    }
                } else if (choice.equals("2")) {
                    signUp();
                } else {
                    System.out.println("Буруу сонголт!");
                }
            }

            System.out.println("Тавтай морил, " + currentUser.getUsername() + "!");
            if (currentUser.getRole() == Role.ADMIN) {
                adminMenu();
            } else {
                userMenu();
            }
            System.out.println("Та системээс гарлаа. Дахин нэвтрэх бол сонголтоо хийнэ үү.");
        }
    }

    static void signUp() {
        System.out.println("=== Бүртгүүлэх ===");
        System.out.print("Хэрэглэгчийн нэр: ");
        String username = sc.nextLine();
        // Check if username already exists
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                System.out.println("Энэ нэртэй хэрэглэгч аль хэдийн байна!");
                return;
            }
        }
        System.out.print("Нууц үг: ");
        String password = sc.nextLine();

        // Admin sign up option
        System.out.print("Админ эрхтэй бүртгүүлэх бол нууц код оруулна уу (эсвэл ENTER дар): ");
        String adminCode = sc.nextLine();
        Role role = Role.USER;
        if (adminCode.equals("kilo")) { 
            role = Role.ADMIN;
        }

        users.add(new User(username, password, role));
        System.out.println("Бүртгэл амжилттай! Одоо нэвтэрнэ үү.");
    }

    static User login() {
        System.out.println("=== Нэвтрэх хэсэг ===");
        System.out.print("Нэвтрэх нэр: ");
        String username = sc.nextLine();
        System.out.print("Нууц үг: ");
        String password = sc.nextLine();

        for (User u : users) {
            if (u.getUsername().equals(username) && u.checkPassword(password)) {
                return u;
            }
        }
        return null;
    }

    static void adminMenu() throws Exception {
        while (true) {
            System.out.println("\n[Админ цэс]");
            System.out.println("1. Бараа нэмэх");
            System.out.println("2. Бараа засах");
            System.out.println("3. Хямдрал тохируулах");
            System.out.println("4. Бараа жагсаалт харах");
            System.out.println("0. Гарах");
            System.out.print("Сонголт: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1: addProduct(); break;
                case 2: editProduct(); break;
                case 3: setDiscount(); break;
                case 4: printCatalog(); break;
                case 0: return;
                default: System.out.println("Буруу сонголт!"); break;
            }
        }
    }

    static void userMenu() throws Exception {
        SaleOrder order = new SaleOrder();
        while (true) {
            System.out.println("\n[Хэрэглэгч цэс]");
            System.out.println("1. Бараа харах");
            System.out.println("2. Захиалга хийх");
            System.out.println("3. Нэхэмжлэл хэвлэх");
            System.out.println("0. Гарах");
            System.out.print("Сонголт: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1: printCatalog(); break;
                case 2: placeOrder(order); break;
                case 3: order.printInvoice(); break;
                case 0: return;
                default: System.out.println("Буруу сонголт!"); break;
            }
        }
    }

    static void addProduct() {
        System.out.print("Барааны нэр: ");
        String name = sc.nextLine();
        double price;
        do {
            System.out.print("Үнэ: ");
            price = Double.parseDouble(sc.nextLine());
            if (price <= 0) System.out.println("Үнэ 0-ээс их байх ёстой!");
        } while (price <= 0);

        int stock;
        do {
            System.out.print("Үлдэгдэл тоо: ");
            stock = Integer.parseInt(sc.nextLine());
            if (stock < 0) System.out.println("Үлдэгдэл 0-с бага байж болохгүй!");
        } while (stock < 0);

        catalog.add(new Product(name, price, stock));
        System.out.println("Бараа амжилттай нэмэгдлээ.");
    }

    static void editProduct() {
        printCatalog();
        System.out.print("Засах барааны индекс: ");
        int idx = Integer.parseInt(sc.nextLine()) - 1;
        if (idx < 0 || idx >= catalog.size()) {
            System.out.println("Буруу индекс!");
            return;
        }
        Product p = catalog.get(idx);

        System.out.print("Шинэ нэр (" + p.getName() + "): ");
        String newName = sc.nextLine();
        if (!newName.isEmpty()) p.setName(newName);

        System.out.print("Шинэ үнэ (" + p.getPrice() + "): ");
        String priceStr = sc.nextLine();
        if (!priceStr.isEmpty()) {
            double newPrice = Double.parseDouble(priceStr);
            if (newPrice > 0) {
                p.setPrice(newPrice);
            } else {
                System.out.println("Үнэ 0-ээс их байх ёстой, шинэчлэх боломжгүй.");
            }
        }

        System.out.print("Шинэ үлдэгдэл тоо (" + p.getRemainingStock() + " үлдсэн): ");
        String stockStr = sc.nextLine();
        if (!stockStr.isEmpty()) {
            int newTotalStock = Integer.parseInt(stockStr);
            p.setTotalStock(newTotalStock);
        }
    }

    static void setDiscount() {
        printCatalog();
        System.out.print("Хямдрал тохируулах барааны индекс: ");
        int idx = Integer.parseInt(sc.nextLine()) - 1;
        if (idx < 0 || idx >= catalog.size()) {
            System.out.println("Буруу индекс!");
            return;
        }
        Product p = catalog.get(idx);

        System.out.print("Хямдралын хувь: ");
        double percent = Double.parseDouble(sc.nextLine());

        System.out.print("Эхлэх огноо (yyyy-MM-dd): ");
        Date start = null, end = null;
        try {
            start = new SimpleDateFormat("yyyy-MM-dd").parse(sc.nextLine());
            System.out.print("Дуусах огноо (yyyy-MM-dd): ");
            end = new SimpleDateFormat("yyyy-MM-dd").parse(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Огнооны формат буруу байна!");
            return;
        }

        DiscountRule rule = new DiscountRule(percent, start, end);
        p.setDiscountRule(rule);
        System.out.println("Хямдрал амжилттай тохируулагдлаа.");
    }

    static void printCatalog() {
        System.out.println("\n--- Барааны жагсаалт ---");
        for (int i = 0; i < catalog.size(); i++) {
            Product p = catalog.get(i);
            System.out.printf("%d. %-15s | Үнэ: %,10.2f₮ | Үлдэгдэл: %d", i + 1, p.getName(), p.getPrice(), p.getRemainingStock());
            if (p.getDiscountRule() != null) {
                System.out.print(" | Хямдрал: " + p.getDiscountRule());
            }
            System.out.println();
        }
    }

    static void placeOrder(SaleOrder order) {
        while (true) {
            printCatalog();
            System.out.print("Захиалах барааны индекс (болих бол 0): ");
            int idx = Integer.parseInt(sc.nextLine()) - 1;
            if (idx == -1) break;
            if (idx < 0 || idx >= catalog.size()) {
                System.out.println("Буруу индекс!");
                continue;
            }
            Product p = catalog.get(idx);
            if (p.getRemainingStock() == 0) {
                System.out.println("Алдаа: Энэ барааны үлдэгдэл дууссан байна!");
                continue;
            }
            int qty;
            do {
                System.out.print("Тоо ширхэг: ");
                qty = Integer.parseInt(sc.nextLine());
                if (qty <= 0) {
                    System.out.println("Тоо ширхэг 0-ээс их байх ёстой!");
                } else if (qty > p.getRemainingStock()) {
                    System.out.println("Алдаа: үлдэгдэл хүрэлцэхгүй байна!");
                    qty = -1;
                }
            } while (qty <= 0);

            OrderItem item = new OrderItem(p, qty);
            order.addItem(item);
            System.out.println("Захиалга амжилттай нэмэгдлээ.");
        }
    }
}
// rm *.class
//javac Main.java
//java Main