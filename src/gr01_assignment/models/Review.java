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
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;
    private String reviewerName;
    private String comment;
    private int rating;

    public Review(String reviewerName, String comment, int rating) {
        this.reviewerName = reviewerName;
        this.comment = comment;
        this.rating = rating;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    //Ghi đè phương thức toString để trả về thông tin của bài đánh giá theo định dạng chuỗi
    @Override
    public String toString() {
        // Định dạng chuỗi với tên người đánh giá, xếp hạng và bình luận
        return "Tên người đánh giá: " + reviewerName + "\nBình luận: " + comment + "\nĐiểm: " + rating;
    }
}
