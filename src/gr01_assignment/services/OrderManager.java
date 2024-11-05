/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr01_assignment.services;

import gr01_assignment.models.Book;
import gr01_assignment.models.Order;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Le Anh Tuan - CE180905
 */
public class OrderManager {

    private final List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void loadOrders(String filename) {
        orders.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length < 3) {
                    continue;
                }

                String customerName = parts[0];
                double totalAmount = Double.parseDouble(parts[1]);
                Map<Book, Integer> purchasedItems = new HashMap<>();

                for (int i = 2; i < parts.length; i += 3) {
                    String productId = parts[i];
                    String bookName = parts[i + 1];
                    int quantity = Integer.parseInt(parts[i + 2]);
                    Book book = new Book(productId, bookName, "", 0, quantity);
                    purchasedItems.put(book, quantity);
                }

                orders.add(new Order(customerName, purchasedItems, totalAmount));
            }
            //ystem.out.println("Orders loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading orders: " + e.getMessage());
        }
    }

    public void saveOrders(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Order order : orders) {
                StringBuilder sb = new StringBuilder();
                sb.append(order.getCustomerName()).append(";").append(order.getTotalAmount()).append(";");
                for (Map.Entry<Book, Integer> entry : order.getPurchasedItems().entrySet()) {
                    Book book = entry.getKey();
                    int quantity = entry.getValue();
                    sb.append(book.getProductId()).append(";").append(book.getName()).append(";").append(quantity).append(";");
                }
                bw.write(sb.toString());
                bw.newLine();
            }
            //System.out.println("Orders saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving orders: " + e.getMessage());
        }
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void displayOrderHistory() {
        if (orders.isEmpty()) {
            System.out.println("\nChưa có lịch sử mua hàng.");
        } else {
            System.out.println("\n--- LỊCH SỬ MUA HÀNG ---");
            for (Order order : orders) {
                System.out.print(order);
                System.out.println("--------------------------\n");
            }
        }
    }
}
