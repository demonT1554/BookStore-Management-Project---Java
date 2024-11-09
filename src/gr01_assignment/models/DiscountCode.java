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
public class DiscountCode implements Serializable {

    private static final long serialVersionUID = 1L;
    private String code;
    private int discountPercentage;
    private int quantity;

    public DiscountCode(String code, int discountPercentage, int quantity) {
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

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
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
        return String.format("| %-10s | %-11d%% | %-11d |",
                code,
                discountPercentage,
                quantity
        );
    }
}
