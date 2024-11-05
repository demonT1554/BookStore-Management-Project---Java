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
public class Book extends Product {

    private String author;
    private int quantity;

    public Book(String productId, String name, String author, double price, int quantity) {
        super(productId, name, price);
        this.author = author;
        this.quantity = quantity;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void decreaseQuantity(int amount) {
        if (quantity >= amount) {
            quantity -= amount;
        }
    }

    public void increaseQuantity(int amount) {
        quantity += amount;
    }

    @Override
    public String toString() {
        return String.format("| %-10s | %-20s | %-19s | %-9.0f VND | %11d |", productId, name, author, price, quantity);
    }
}
