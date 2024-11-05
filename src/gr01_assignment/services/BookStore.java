/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr01_assignment.services;

import gr01_assignment.models.Book;
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
public class BookStore {

    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void listBooks() {
        System.out.println("\n--- DANH SÁCH SÁCH TRONG KHO ---");
        System.out.println("+------------+----------------------+---------------------+---------------+-------------+");
        System.out.println("| Mã sách    | Tên sách             | Tác giả             | Giá           | Số lượng    |");
        System.out.println("+------------+----------------------+---------------------+---------------+-------------+");
        for (Book book : books) {
            System.out.println(book);
            System.out.println("+------------+----------------------+---------------------+---------------+-------------+");
        }
    }

    public Book findBookById(String productId) {
        return books.stream().filter(b -> b.getProductId().equals(productId)).findFirst().orElse(null);
    }

    public void updateBook(String productId, int newQuantity, double newPrice) {
        Book book = findBookById(productId);
        if (book != null) {
            book.setQuantity(newQuantity);
            book.setPrice(newPrice);
            System.out.println("Đã cập nhật sách: " + book.getName());
        } else {
            System.out.println("Không tìm thấy sách với mã: " + productId);
        }
    }

    public void deleteBook(String productId) {
        Book book = findBookById(productId);
        if (book != null) {
            books.remove(book);
            System.out.println("Đã xóa sách: " + book.getName());
        } else {
            System.out.println("Không tìm thấy sách với mã: " + productId);
        }
    }

    // Phương thức đọc sách từ tệp .txt
    public void loadBooksFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Book book = new Book(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), Integer.parseInt(parts[4]));
                    addBook(book);
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc tệp: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Lỗi định dạng trong tệp sách: " + e.getMessage());
        }
    }

    public void saveBooksToFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Book book : books) {
                bw.write(String.join(",", book.getProductId(), book.getName(), book.getAuthor(),
                        String.valueOf(book.getPrice()), String.valueOf(book.getQuantity())));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc tệp: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Lỗi định dạng trong tệp sách: " + e.getMessage());
        }
    }
}
