/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr01_assignment.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Le Anh Tuan - CE180905
 */
public class Password {

    private String ownerPassword = "admin";
    private String staffPassword = "nhanvien";

    // Phương thức để đọc mật khẩu từ tệp
    public void loadPasswords(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            ownerPassword = reader.readLine();
            staffPassword = reader.readLine();
        } catch (IOException e) {
            System.out.println("Không thể tải mật khẩu từ file, sử dụng mật khẩu mặc định.");
        }
    }

    // Phương thức để lưu mật khẩu vào tệp
    public void savePasswords(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(ownerPassword);
            writer.newLine();
            writer.write(staffPassword);
        } catch (IOException e) {
            System.out.println("Không thể lưu mật khẩu vào file.");
        }
    }
        // Phương thức kiểm tra mật khẩu chủ quán
    public String getOwnerPassword() {
        return ownerPassword;
    }

    public void setOwnerPassword(String ownerPassword) {
        this.ownerPassword = ownerPassword;
    }

    public String getStaffPassword() {
        return staffPassword;
    }

    public void setStaffPassword(String staffPassword) {
        this.staffPassword = staffPassword;
    }
}
