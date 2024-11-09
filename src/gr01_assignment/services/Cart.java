/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr01_assignment.services;

import gr01_assignment.models.Book;
import gr01_assignment.utils.InputValidator;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Le Anh Tuan - CE180905
 */
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<Book, Integer> cartItems = new HashMap<>();
    
    public void addBookToCart(Book book, int quantity) {
        if (book == null) {  
            System.out.println("Sách không tồn tại.");
            return;
        }
        if (cartItems == null) { 
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
            cartItems.remove(book); // Xóa hoàn toàn sách khỏi giỏ
            System.out.println("Đã xóa hoàn toàn sách " + book.getName() + " khỏi giỏ hàng.");
        } else {
            cartItems.put(book, currentQuantity - quantityToRemove);    // Cập nhật số lượng mới
            System.out.println("Đã xóa " + quantityToRemove + " quyển sách " + book.getName() + " khỏi giỏ hàng.");
        }
        book.increaseQuantity(quantityToRemove);
    }
    public void listCartItems() {
        System.out.println("SÁCH TRONG GIỎ HÀNG");
        for (Map.Entry<Book, Integer> entry : cartItems.entrySet()) {
            Book book = entry.getKey();
            int quantity = entry.getValue();
            String itemInfo = String.format("Tên sách: %s, Giá: %d VND, Số lượng: %d",
                    book.getName(),
                    book.getPrice(),
                    quantity
            );
            System.out.println(itemInfo);
        }
    }

    public int calculateTotal() {
        int total = 0;
        for (Map.Entry<Book, Integer> entry : cartItems.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
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
        return null; 
    }

    public void updateBookQuantity(Book book, int quantity) {
        if (cartItems.containsKey(book)) {
            if (quantity > 0) {
                cartItems.put(book, quantity); 
                System.out.println("Cập nhật số lượng sách " + book.getName() + " thành " + quantity);
            } else {
                System.out.println("Số lượng phải lớn hơn 0.");
            }
        } else {
            System.out.println("Sách không có trong giỏ hàng.");
        }
    }

    public int calculateComboDiscount() {
        int totalBooks = getTotalQuantity(); 
        if (totalBooks >= 50) {
            return 15;
        } else if (totalBooks >= 40) {
            return 14;
        } else if (totalBooks >= 30) {
            return 13;
        } else if (totalBooks >= 20) {
            return 12;
        } else if (totalBooks >= 10) {
            return 11;
        } else {
            return 0;
        }
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    public int getTotalQuantity() {
        return cartItems.values().stream().mapToInt(Integer::intValue).sum();
    }
}
