/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr01_assignment.services;

import gr01_assignment.models.Book;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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

    private final List<Book> books = new ArrayList<>();
    private static final String BOOKS_FILE = "books.txt";
    private static final String TEMP_FILE = "books_temp.txt";

    public void addBookToInventory(Book book) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE, true))) {
            String bookData = String.format("%s,%s,%s,%d,%d",
                    book.getProductId(),
                    book.getName(),
                    book.getAuthor(),
                    book.getPrice(),
                    book.getQuantity());
            writer.write(bookData);
            writer.newLine();
            books.add(book);
            System.out.println("Sách đã được thêm vào kho thành công.");
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi vào file: " + e.getMessage());
        }
    }

    public void listBooks() {
        System.out.println("\n-------------------------------- DANH SÁCH SÁCH TRONG KHO -------------------------------");
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

    public void updateBookInInventory(String productId, int newQuantity, int newPrice) {
        //System.out.printf("Updating book %s with new quantity: %d and price: %d%n", productId, newQuantity, newPrice);
        File inputFile = new File("books.txt");
        File tempFile = new File("books_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] bookData = currentLine.split(",");
                if (bookData[0].equals(productId)) {
                    bookData[3] = String.valueOf(newPrice);
                    bookData[4] = String.valueOf(newQuantity);
                    currentLine = String.join(",", bookData);
                }
                writer.write(currentLine);
                writer.newLine();
            }

            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
            } else {
                System.out.println("Lỗi khi đổi tên file tạm.");
            }

            for (Book book : books) {
                if (book.getProductId().equals(productId)) {
                    book.setPrice(newPrice);
                    book.setQuantity(newQuantity);
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Lỗi khi xử lý file: " + e.getMessage());
        }
    }

    public void removeBookFromInventory(String productId, int quantityToRemove) {
        List<String> fileContent = new ArrayList<>();
        boolean found = false;
        boolean bookExists = false;
        int remainingQuantity = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] bookData = currentLine.split(",");
                if (bookData[0].equals(productId)) {
                    bookExists = true;
                    int currentQuantity = Integer.parseInt(bookData[4]);
                    if (quantityToRemove > currentQuantity) {
                        System.out.println("Số lượng sách vượt quá số lượng trong kho. Hiện tại trong kho có " + currentQuantity + " sách.");
                        return;
                    } else if (quantityToRemove == currentQuantity) {
                        System.out.println("Sách đã bị xóa hoàn toàn khỏi kho.");
                        found = true;
                        continue;
                    } else {
                        bookData[4] = String.valueOf(currentQuantity - quantityToRemove);
                        currentLine = String.join(",", bookData);
                        remainingQuantity = currentQuantity - quantityToRemove;
                        found = true;
                        System.out.println("Đã xóa " + quantityToRemove + " sách. Số lượng còn lại: " + remainingQuantity);
                    }
                }
                fileContent.add(currentLine);
            }
            if (!bookExists) {
                System.out.println("Mã sách '" + productId + "' không có trong kho.");
                return;
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_FILE))) {
            for (String line : fileContent) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi vào file tạm: " + e.getMessage());
            return;
        }
        File originalFile = new File(BOOKS_FILE);
        File tempFile = new File(TEMP_FILE);
        if (originalFile.delete()) {
            if (!tempFile.renameTo(originalFile)) {
                //System.out.println("Không thể đổi tên file tạm.");
            }
        } else {
            System.out.println("Không thể cập nhật file sách.");
        }
        if (found) {
            books.removeIf(book -> book.getProductId().equals(productId) && book.getQuantity() <= quantityToRemove);
            for (Book book : books) {
                if (book.getProductId().equals(productId)) {
                    book.setQuantity(remainingQuantity);
                    break;
                }
            }
        }
    }

    public void loadBooksFromFile(String fileName) {
        books.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String productId = parts[0];
                    String name = parts[1];
                    String author = parts[2];
                    int price = Integer.parseInt(parts[3]);
                    int quantity = Integer.parseInt(parts[4]);
                    Book book = new Book(productId, name, author, price, quantity);
                    books.add(book);
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc dữ liệu sách: " + e.getMessage());
        }
    }

    public void saveBooksToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false))) { // 'false' để ghi đè file cũ
            for (Book book : books) {
                String bookLine = String.format("%s,%s,%s,%d,%d",
                        book.getProductId(),
                        book.getName(),
                        book.getAuthor(),
                        book.getPrice(),
                        book.getQuantity());
                writer.write(bookLine);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi dữ liệu sách: " + e.getMessage());
        }
    }
}
