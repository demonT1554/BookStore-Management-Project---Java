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
    }

    public void listDiscountCodes() {
        System.out.println("\n--- DANH SÁCH MÃ GIẢM GIÁ ---");
        System.out.println("+------------+--------------+-------------+");
        System.out.println("| Mã         | Giảm giá     | Số lượng    |");
        System.out.println("+------------+--------------+-------------+");
        for (DiscountCode discountCode : discountCodes) {
            System.out.println(discountCode);
            System.out.println("+------------+--------------+-------------+");
        }
    }

    public DiscountCode findDiscountCode(String code) {
        return discountCodes.stream()
                .filter(d -> d.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);
    }

    public void updateDiscountCode(String code, double percentage, int quantity) {
        for (DiscountCode discount : discountCodes) {
            if (discount.getCode().equalsIgnoreCase(code)) {
                // Cập nhật giá trị của mã giảm giá
                discount.setDiscountPercentage(percentage);
                System.out.println("Đã cập nhật mã giảm giá: " + code);
                return;
            }
        }
        System.out.println("Không tìm thấy mã giảm giá với mã: " + code);
    }

    public void deleteDiscountCode(String code) {
        DiscountCode toRemove = null;
        for (DiscountCode discount : discountCodes) {
            if (discount.getCode().equalsIgnoreCase(code)) {
                toRemove = discount;
                break;
            }
        }
        if (toRemove != null) {
            discountCodes.remove(toRemove);
            System.out.println("Đã xóa mã giảm giá: " + code);
        } else {
            System.out.println("Không tìm thấy mã giảm giá với mã: " + code);
        }
    }

    public void loadDiscounts(String filename) {
        discountCodes.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String code = parts[0].trim();
                    double percentage = Double.parseDouble(parts[1].trim()) / 100; // Chuyển từ phần trăm sang hệ số
                    int quantity = Integer.parseInt(parts[2].trim());
                    discountCodes.add(new DiscountCode(code, percentage, quantity));
                }
            }
            //System.out.println("Discounts loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading discounts: " + e.getMessage());
        }
    }

    public void saveDiscounts(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (DiscountCode discountCode : discountCodes) {
                // Lưu dưới dạng "mã,tỷ lệ phần trăm,số lượng"
                bw.write(discountCode.getCode() + ","
                        + (int) (discountCode.getDiscountPercentage() * 100) + ","
                        + discountCode.getQuantity());
                bw.newLine();
            }
            //System.out.println("Discounts saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving discounts: " + e.getMessage());
        }
    }

    public void removeDiscountCode(String code) {
        discountCodes.removeIf(discountCode -> discountCode.getCode().equalsIgnoreCase(code));
    }
}
