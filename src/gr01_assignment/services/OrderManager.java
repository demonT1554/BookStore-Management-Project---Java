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

    private final List<Order> orderHistory = new ArrayList<>();

    public void addOrder(Order order, String filename) {
        orderHistory.add(order);
        saveOrdersToFile(filename);
    }

    public void loadOrdersFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String customerName = null;
            int totalAmount = 0;
            Map<Book, Integer> items = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    if (customerName != null) {
                        orderHistory.add(new Order(customerName, items, totalAmount));
                    }
                    customerName = null;
                    items = new HashMap<>();
                    continue;
                }
                if (customerName == null) {
                    String[] parts = line.split(",");
                    customerName = parts[0].trim();
                    totalAmount = Integer.parseInt(parts[1].trim());
                } else {
                    String[] parts = line.split(",");
                    String name = parts[0].trim();
                    int price = Integer.parseInt(parts[1].trim());
                    int quantity = Integer.parseInt(parts[2].trim());
                    Book book = new Book("TEMP_ID", name, "UNKNOWN_AUTHOR", price, quantity);
                    items.put(book, quantity);
                }
            }
            if (customerName != null) {
                orderHistory.add(new Order(customerName, items, totalAmount));
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc dữ liệu đơn hàng: " + e.getMessage());
        }
    }

    public void saveOrdersToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Order order : orderHistory) {
                // Ghi tên khách hàng và tổng tiền
                writer.write(order.getCustomerName() + "," + order.getTotalAmount());
                writer.newLine();
                // Ghi danh sách sách đã mua
                for (Map.Entry<Book, Integer> entry : order.getItems().entrySet()) {
                    Book book = entry.getKey();
                    int quantity = entry.getValue();
                    writer.write(book.getName() + "," + book.getPrice() + "," + quantity);
                    writer.newLine();
                }
                writer.newLine(); // Thêm dòng trống giữa các đơn hàng
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi dữ liệu đơn hàng: " + e.getMessage());
        }
    }

    public void displayOrderHistory() {
        if (orderHistory.isEmpty()) {
            System.out.println("\nChưa có lịch sử mua hàng.");
        } else {
            System.out.println("\n--- LỊCH SỬ MUA HÀNG ---");
            for (Order order : orderHistory) {
                System.out.println("Khách hàng: " + order.getCustomerName());
                System.out.println("Tổng tiền: " + order.getTotalAmount());
                System.out.println("Danh sách sách:");
                for (Map.Entry<Book, Integer> entry : order.getItems().entrySet()) {
                    Book book = entry.getKey();
                    int quantity = entry.getValue();
                    System.out.printf(" - %s (Số lượng: %d, Giá: %d)\n", book.getName(), quantity, book.getPrice());
                }
                System.out.println("--------------------------\n");
            }
        }
    }
}
