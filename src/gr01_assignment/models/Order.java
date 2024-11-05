/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr01_assignment.models;

import java.util.Map;

/**
 *
 * @author Le Anh Tuan - CE180905
 */
public class Order {

    private String customerName;
    private Map<Book, Integer> purchasedItems;
    private double totalAmount;

    public Order(String customerName, Map<Book, Integer> purchasedItems, double totalAmount) {
        this.customerName = customerName;
        this.purchasedItems = purchasedItems;
        this.totalAmount = totalAmount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Map<Book, Integer> getPurchasedItems() {
        return purchasedItems;
    }

    public void setPurchasedItems(Map<Book, Integer> purchasedItems) {
        this.purchasedItems = purchasedItems;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Khách hàng:  ").append(customerName)
                .append("\nTổng số tiền: ").append(String.format("%.0f VND", totalAmount)).append("\nMặt hàng đã mua:\n");
        purchasedItems.forEach((book, qty) -> sb.append(book.getName()).append("\nSố lượng: ").append(qty).append("\n"));
        return sb.toString();
    }

}
