/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr01_assignment.services;

import gr01_assignment.models.Book;
import gr01_assignment.utils.InputValidator;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Le Anh Tuan - CE180905
 */
//Lớp Cart để quản lý danh sách các sản phẩm mà khách hàng muốn mua
public class Cart {

    private Map<Book, Integer> cartItems = new HashMap<>();

    public void addBookToCart(Book book, int quantity) {
        if (book == null) {  // Kiểm tra nếu book là null
            System.out.println("Sách không tồn tại.");
            return;
        }
        if (cartItems == null) {  // Khởi tạo cartItems nếu chưa khởi tạo
            cartItems = new HashMap<>();
        }
        if (book.getQuantity() >= quantity) {
            cartItems.put(book, cartItems.getOrDefault(book, 0) + quantity);
            book.decreaseQuantity(quantity);
            System.out.println(quantity + " quyển sách " + book.getName() + " đã được thêm vào giỏ hàng.");
        } else {
            System.out.println("Không đủ sách trong kho.");
        }
    }

    public void removeBookFromCart(Book book) {
        if (!cartItems.containsKey(book)) {
            System.out.println("Sách không có trong giỏ hàng.");
            return;
        }

        if (cartItems.isEmpty()) {
            System.out.println("Giỏ hàng trống. Không thể xóa sách.");
            return;
        }
        int currentQuantity = cartItems.get(book);
        int quantityToRemove = InputValidator.getPositiveInt("Nhập số lượng sách cần xóa: ");

        if (quantityToRemove > currentQuantity) {
            System.out.println("Số lượng cần xóa vượt quá số lượng trong giỏ hàng.");
        } else if (quantityToRemove == currentQuantity) {
            cartItems.remove(book);
            System.out.println("Đã xóa hoàn toàn sách " + book.getName() + " khỏi giỏ hàng.");
        } else {
            cartItems.put(book, currentQuantity - quantityToRemove);
            System.out.println("Đã xóa " + quantityToRemove + " quyển sách " + book.getName() + " khỏi giỏ hàng.");
        }

        book.increaseQuantity(quantityToRemove); // Hoàn lại số lượng vào kho
    }

    public void listCartItems() {

        System.out.println("--- SÁCH TRONG GIỎ HÀNG ---");
        for (Map.Entry<Book, Integer> entry : cartItems.entrySet()) {
            Book book = entry.getKey();
            int quantity = entry.getValue();
            String itemInfo = String.format("Tên sách: %s, Giá: %.0f VND, Số lượng: %d",
                    book.getName(),
                    book.getPrice(),
                    quantity
            );
            System.out.println(itemInfo);

        }
    }

    public double calculateTotal() {
        return cartItems.entrySet().stream().mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue()).sum();
    }

    public void clearCart() {
        cartItems.clear();
    }

    public Map<Book, Integer> getCartItems() {
        return cartItems;
    }

    public Book getBookInCart(String productId) {
        for (Book book : cartItems.keySet()) {
            if (book.getProductId().equalsIgnoreCase(productId)) {
                return book;
            }
        }
        return null; // Trả về null nếu không tìm thấy sách với productId
    }

    public void updateBookQuantity(Book book, int quantity) {
        if (cartItems.containsKey(book)) {
            if (quantity > 0) {
                cartItems.put(book, quantity); // Cập nhật số lượng mới cho sách
                System.out.println("Cập nhật số lượng sách " + book.getName() + " thành " + quantity);
            } else {
                System.out.println("Số lượng phải lớn hơn 0.");
            }
        } else {
            System.out.println("Sách không có trong giỏ hàng.");
        }
    }

    public double calculateComboDiscount() {
        int totalBooks = getTotalQuantity(); // Giả sử bạn có phương thức để lấy tổng số sách trong giỏ
        if (totalBooks >= 50) {
            return 0.15;
        } else if (totalBooks >= 40) {
            return 0.14;
        } else if (totalBooks >= 30) {
            return 0.13;
        } else if (totalBooks >= 20) {
            return 0.12;
        } else if (totalBooks >= 10) {
            return 0.11;
        } else {
            return 0.0;
        }
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    public int getTotalQuantity() {
        return cartItems.values().stream().mapToInt(Integer::intValue).sum();
    }

}
