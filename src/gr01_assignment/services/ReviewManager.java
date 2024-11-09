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

    public void addReview(Review review) {
        reviews.add(review);
        saveReviewsToFile("reviews.txt");
        System.out.println("Đánh giá đã được thêm thành công.\n");
    }

    public void loadReviewsFromFile(String fileName) {
        reviews.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String reviewerName = parts[0];
                    String comment = parts[1];
                    int rating = Integer.parseInt(parts[2]);
                    Review review = new Review(reviewerName, comment, rating);
                    reviews.add(review);
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc dữ liệu đánh giá: " + e.getMessage());
        }
    }

    public void saveReviewsToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Review review : reviews) {
                writer.write(String.format("%s,%s,%d",
                        review.getReviewerName(),
                        review.getComment(),
                        review.getRating()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi dữ liệu đánh giá: " + e.getMessage());
        }
    }

    public void displayReviews() {
        if (reviews.isEmpty()) {
            System.out.println("Chưa có đánh giá nào.");
        } else {
            System.out.println("\n--- DANH SÁCH ĐÁNH GIÁ ---");
            for (Review review : reviews) {
                System.out.println(review);
                System.out.println("--------------------------");
            }
        }
    }

    public void addReviewFromCustomer(Scanner scanner, String reviewerName) {
        int rating = InputValidator.getPositiveIntRange("Nhập đánh giá của bạn (1 đến 5): ");
        String comment = InputValidator.getStringInput("Nhập bình luận: ");

        Review newReview = new Review(reviewerName, comment, rating);
        addReview(newReview); 
        System.out.println("Cảm ơn " + reviewerName + " đã để lại đánh giá!\n");
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void removeReviewByReviewer(String reviewerName) {
        boolean removed = reviews.removeIf(review -> review.getReviewerName().equalsIgnoreCase(reviewerName));
        if (removed) {
            saveReviewsToFile("reviews.txt");
            System.out.println("Đánh giá của " + reviewerName + " đã được xóa.");
        } else {
            System.out.println("Không tìm thấy đánh giá của " + reviewerName + ".");
        }
    }

    public void updateReview(String reviewerName, int newRating, String newComment) {
        for (Review review : reviews) {
            if (review.getReviewerName().equalsIgnoreCase(reviewerName)) {
                review.setRating(newRating);
                review.setComment(newComment);
                saveReviewsToFile("reviews.txt"); 
                System.out.println("Đánh giá của " + reviewerName + " đã được cập nhật.");
                return;
            }
        }
        System.out.println("Không tìm thấy đánh giá của " + reviewerName + ".");
    }
}
