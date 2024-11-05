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
public class DiscountCode {

    private String code;
    private double discountPercentage;
    private int quantity;

    public DiscountCode(String code, double discountPercentage, int quantily) {
        this.code = code;
        this.discountPercentage = discountPercentage;
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    

    @Override
    public String toString() {
        return String.format("| %-10s | %-11.0f%% | %11d |",
                code,
                discountPercentage * 100,
                quantity
        );
    }
}
