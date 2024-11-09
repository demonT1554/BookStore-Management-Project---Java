/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr01_assignment.models;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author Le Anh Tuan - CE180905
 */
//Đây là lớp Order, đại diện cho một đơn đặt hàng
//Đơn hàng chứa thông tin khách hàng, các mặt hàng đã mua và tổng số tiền
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    private String customerName; // Tên khách hàng
    private Map<Book, Integer> items; // Danh sách sách và số lượng mua
    private int totalAmount; // Tổng giá trị đơn hàng (lưu dưới dạng int để nhất quán)

    public Order(String customerName, Map<Book, Integer> items, int totalAmount) {
        this.customerName = customerName;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Map<Book, Integer> getItems() {
        return items;
    }

    public void setPurchasedItems(Map<Book, Integer> getItems) {
        this.items = items;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Khách hàng: ")
                .append(customerName) 
                .append("\nTổng tiền: ").append(totalAmount).append(" VND") 
                .append("\nDanh sách mặt hàng:\n");
        items.forEach((book, qty) -> sb
                .append("- ").append(book.getName()).append(" (Số lượng: ").append(qty).append(", Giá: ").append(book.getPrice()).append(" VND)\n")
        );
        return sb.toString();
    }
}
