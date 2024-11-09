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
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    protected String productId;
    protected String name;
    protected int price;

    public Product(String productId, String name, int price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    //Ghi đè phương thức toString để trả về thông tin về sản phẩm theo định dạng chuỗi
    @Override
    public String toString() {
        // Định dạng chuỗi với các thông tin: Mã sách, Tên sách, Giá
        return String.format("Mã sách: %s, Tên sách: %s, Giá: %d VND",
                productId,
                name,
                price
        );
    }

}
