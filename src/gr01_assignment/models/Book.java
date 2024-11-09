/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr01_assignment.models;

import java.io.Serializable;

/**
 *
 * @author Le Anh Tuan - CE180905
 */
//Đây là lớp Book, kế thừa từ lớp Product
//Mỗi đối tượng Book đại diện cho một cuốn sách với các thông tin cụ thể như tác giả và số lượng
public class Book extends Product implements Serializable {

    private static final long serialVersionUID = 1L;
    // Thuộc tính lưu tên tác giả của cuốn sách
    private String author;
    // Thuộc tính lưu số lượng của cuốn sách trong kho
    private int quantity;

    public Book(String productId, String name, String author, int price, int quantity) {
        super(productId, name, price);
        this.author = author;
        this.quantity = quantity;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void decreaseQuantity(int amount) {
        // Kiểm tra nếu số lượng hiện tại đủ lớn để giảm
        if (quantity >= amount) {
            quantity -= amount;
        }
    }

    public void increaseQuantity(int amount) {
        //Tăng số lượng sách trong kho
        quantity += amount;
    }

    //Ghi đè phương thức toString để trả về thông tin về cuốn sách theo định dạng chuỗi
    @Override
    public String toString() {
        // Định dạng chuỗi với các cột: ID sản phẩm, Tên, Tác giả, Giá, Số lượng
        return String.format("| %-10s | %-20s | %-19s | %-9d VND | %11d |",
                productId,
                name,
                author,
                price,
                quantity
        );
    }
}
