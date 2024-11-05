/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr01_assignment.models;

/**
 *
 * @author Le Anh Tuan - CE180905
 */
public class Review {
    
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

    
    
    @Override
    public String toString() {
        return reviewerName + "," + rating + "," + comment;
    }
    
    
    
}
