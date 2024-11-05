/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr01_assignment.utils;

import java.util.Scanner;

/**
 *
 * @author Le Anh Tuan - CE180905
 */
public class InputValidator {

    private static Scanner scanner = new Scanner(System.in);

    public static int getPositiveInt(String message) {
        int number = -1;
        while (number <= 0) {
            System.out.print(message);
            try {
                number = Integer.parseInt(scanner.nextLine());
                if (number <= 0) {
                    System.out.println("Dữ liệu nhập vào không hợp lệ.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Dữ liệu nhập vào không hợp lệ.");
            }
        }
        return number;
    }
    
    public static int getPositiveIntRange(String message) {
        int number = -1;
        while (number <= 0 || number > 5) {
            System.out.print(message);
            try {
                number = Integer.parseInt(scanner.nextLine());
                if (number <= 0 || number > 5) {
                    System.out.println("Dữ liệu nhập vào không hợp lệ.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Dữ liệu nhập vào không hợp lệ.");
            }
        }
        return number;
    }

    public static double getPositiveDouble(String message) {
        double number = -1;
        while (number <= 0) {
            System.out.print(message);
            try {
                number = Double.parseDouble(scanner.nextLine());
                if (number <= 0) {
                    System.out.println("Dữ liệu nhập vào không hợp lệ.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Dữ liệu nhập vào không hợp lệ.");
            }
        }
        return number;
    }
    
    public static String getStringInput(String message) {
        String input;
        do {
            System.out.print(message);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Dữ liệu nhập vào không hợp lệ.");
            }
        } while (input.isEmpty());
        return input;
    }
}
