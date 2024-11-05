/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr01_assignment.services;

import gr01_assignment.models.Review;
import gr01_assignment.utils.InputValidator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Le Anh Tuan - CE180905
 */
public class ReviewManager {

    private List<Review> reviews = new ArrayList<>();
    private boolean allowReview = false;

    public void addReview(Review review) {
        reviews.add(review);
    }

    public void loadReviews(String filename) {
        reviews.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String reviewerName = parts[0];
                    int rating = Integer.parseInt(parts[1]);
                    String comment = parts[2];
                    reviews.add(new Review(reviewerName, comment, rating));
                }
            }
            //System.out.println("Reviews sloaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading reviews: " + e.getMessage());
        }
    }

    public void saveReviews(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Review review : reviews) {
                bw.write(review.toString());
                bw.newLine();
            }
            //System.out.println("Reviews saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving reviews: " + e.getMessage());
        }
    }

    // Hiển thị danh sách đánh giá
    public void displayReviews() {
        if (reviews.isEmpty()) {
            System.out.println("Chưa có đánh giá nào cho cửa hàng.");
        } else {
            System.out.println("\n--- DANH SÁCH ĐÁNH GIÁ ---");
            for (Review review : reviews) {
                System.out.println(review.toString());
                System.out.println("----------------------------");
            }
        }
    }

    public void rateStore(Scanner scanner, String customerName) {
        if (!allowReview) {
            System.out.println("Bạn cần hoàn tất thanh toán trước khi đánh giá cửa hàng.");
            return;
        }

        int rating = InputValidator.getPositiveIntRange("Nhập đánh giá của bạn (1 đến 5): ");
        String comment = InputValidator.getStringInput("Nhập bình luận: ");

        reviews.add(new Review(customerName, comment, rating));
        System.out.println("Cảm ơn " + customerName + " đã để lại đánh giá!");
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setAllowReview(boolean allow) {
        this.allowReview = allow;
    }
}
