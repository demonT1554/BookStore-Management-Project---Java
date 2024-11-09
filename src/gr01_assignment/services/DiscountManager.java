/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr01_assignment.services;

import gr01_assignment.models.DiscountCode;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Le Anh Tuan - CE180905
 */
public class DiscountManager {

    private List<DiscountCode> discountCodes = new ArrayList<>();

    public void addDiscountCode(DiscountCode discountCode) {
        discountCodes.add(discountCode);
        saveDiscountsToFile("discounts.txt"); 
        System.out.println("Mã giảm giá đã được thêm thành công.");
    }

    public void listDiscountCodes() {
        System.out.println("\n---------- DANH SÁCH MÃ GIẢM GIÁ ----------");
        System.out.println("+------------+--------------+-------------+");
        System.out.println("| Mã         | Giảm giá     | Số lượng    |");
        System.out.println("+------------+--------------+-------------+");
        for (DiscountCode discountCode : discountCodes) {
            System.out.println(discountCode);
            System.out.println("+------------+--------------+-------------+");
        }
    }

    public DiscountCode findDiscountCode(String code) {
        return discountCodes.stream().filter(d -> d.getCode().equals(code)).findFirst().orElse(null);
    }

    public void updateDiscountCode(String code, int percentage, int quantity) {
        boolean found = false;
        for (DiscountCode discount : discountCodes) {
            if (discount.getCode().equals(code)) {
                discount.setDiscountPercentage(percentage);
                discount.setQuantity(quantity);
                found = true;
                break;
            }
        }
        if (found) {
            saveDiscountsToFile("discounts.txt");
            System.out.println("Mã giảm giá đã được cập nhật.");
        } else {
            System.out.println("Không tìm thấy mã giảm giá: " + code);
        }
    }

    public void deleteDiscountCode(String code, int quantityToRemove) {
        boolean found = false;
        for (DiscountCode discount : discountCodes) {
            if (discount.getCode().equals(code)) {
                int currentQuantity = discount.getQuantity();
                if (quantityToRemove > currentQuantity) {
                    System.out.println("Số lượng mã giảm giá vượt quá số lượng trong kho. Hiện tại trong kho có " + currentQuantity + " mã.");
                    return;
                } else if (quantityToRemove == currentQuantity) {
                    discountCodes.remove(discount); 
                    System.out.println("Mã giảm giá đã bị xóa hoàn toàn khỏi kho.");
                } else {
                    discount.setQuantity(currentQuantity - quantityToRemove); 
                    System.out.println("Đã xóa " + quantityToRemove + " mã giảm giá. Số lượng còn lại: " + (currentQuantity - quantityToRemove));
                }
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Không tìm thấy mã giảm giá: " + code);
        }
        saveDiscountsToFile("discounts.txt");
    }

    public void loadDiscountsFromFile(String fileName) {
        discountCodes.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String code = parts[0];
                    int percentage = Integer.parseInt(parts[1]);
                    int quantity = Integer.parseInt(parts[2]);
                    discountCodes.add(new DiscountCode(code, percentage, quantity));
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc dữ liệu mã giảm giá: " + e.getMessage());
        }
    }

    public void saveDiscountsToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (DiscountCode discount : discountCodes) {
                writer.write(String.format("%s,%d,%d",
                        discount.getCode(),
                        discount.getDiscountPercentage(),
                        discount.getQuantity()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi dữ liệu mã giảm giá: " + e.getMessage());
        }
    }

    public boolean applyDiscountAndReduceQuantity(String code) {
        DiscountCode discount = findDiscountCode(code);
        if (discount != null && discount.getQuantity() > 0) {
            if (discount.getQuantity() == 1) {
                discountCodes.remove(discount);
            } else {
                discount.setQuantity(discount.getQuantity() - 1);
            }
            saveDiscountsToFile("discounts.txt");
            System.out.println("Đã áp dụng mã giảm giá: " + code);
            return true;
        } else {
            System.out.println("Mã giảm giá không hợp lệ hoặc đã hết số lượng.");
            return false;
        }
    }

}
