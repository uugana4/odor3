import java.util.*;

public class Onlyshop {
    static Scanner sc = new Scanner(System.in);
    static List<User> users = new ArrayList<>();
    static List<Product> catalog = new ArrayList<>();

    public static void main(String[] args) throws Exception {
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

    // User menu method stub
    public static void userMenu() {
        System.out.println("Хэрэглэгчийн цэс (userMenu) дуудагдлаа. Энд хэрэглэгчийн үйлдлүүдийг хэрэгжүүлнэ.");
        // TODO: Хэрэглэгчид зориулсан үйлдлүүдийг энд нэмнэ үү.
    }

    // Admin menu method stub
    public static void adminMenu() {
        System.out.println("Админ цэс (adminMenu) дуудагдлаа. Энд админ үйлдлүүдийг хэрэгжүүлнэ.");
        // TODO: Админд зориулсан үйлдлүүдийг энд нэмнэ үү.
    }

    // ... Энд таны бүх бусад методууд adminMenu(), userMenu(), addProduct(), editProduct(), setDiscount(), printCatalog(), placeOrder() гэх мэт байх болно ...
    // Тэдгээрийг мөн тусдаа файлуудад салгах шаардлагагүй, main класс дотор хэвээрээ байж болно.

    // Login method implementation
    public static User login() {
        System.out.print("Хэрэглэгчийн нэр: ");
        String username = sc.nextLine();
        System.out.print("Нууц үг: ");
        String password = sc.nextLine();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.checkPassword(password)) {
                return user;
            }
        }
        return null;
    }

    // Sign Up method implementation
    public static void signUp() {
        System.out.print("Шинэ хэрэглэгчийн нэр: ");
        String username = sc.nextLine();
        System.out.print("Нууц үг: ");
        String password = sc.nextLine();
        // Check if username already exists
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Энэ нэртэй хэрэглэгч аль хэдийн бүртгэлтэй байна.");
                return;
            }
        }
        users.add(new User(username, password, Role.USER));
        System.out.println("Шинэ хэрэглэгч амжилттай бүртгэгдлээ!");
    }
    
    // Та хүсвэл эдгээрийг өөр файлуудад салгаж ч болно, гэхдээ main класс ихэвчлэн нэг файлд байхад амар байдаг.

    // Жич: import болон бусад бүх хэрэгтэй классуудыг импорт хийсэн байгаарай.
}
