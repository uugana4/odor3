// Main.java
public class Main {
    public static void main(String[] args) {
        try {
            // Тэгш өнцөгт гурвалжин үүсгэж байна
            TegshUntsugtGurvaljin triangle = new TegshUntsugtGurvaljin(-5, 8); // Алдаатай утга оруулсан

            // Мэдээлэл хэвлэх
            triangle.hevleh();
        } catch (IllegalArgumentException e) {
            // Алдаа гарсан тохиолдолд энэ хэсэг ажиллана
            System.out.println("Алдаа: " + e.getMessage());
        }
    }
}
