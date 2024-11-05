/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr01_assignment.services;

import gr01_assignment.models.Book;
import gr01_assignment.models.DiscountCode;
import gr01_assignment.models.Order;
import gr01_assignment.models.Review;
import gr01_assignment.utils.InputValidator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Le Anh Tuan - CE180905
 */
public class GR01_Assignment {

    private static final Cart cart = new Cart();
    private static final String STAFF_PASSWORD = "123"; // Mật khẩu nhân viên
    private static final List<Review> storeReviews = new ArrayList<>(); // Đánh giá của cửa hàng
    private static final List<Order> orderHistory = new ArrayList<>(); // Lịch sử mua hàng
    private static boolean allowReview = false;
    private static String customerName;
    private static final DiscountManager discountManager = new DiscountManager();
    private static final OrderManager orderManager = new OrderManager();
    private static final BookStore bookStore = new BookStore();
    private static final ReviewManager reviewManager = new ReviewManager();

    public static void main(String[] args) {
        loadInitialData();
        displayMenu();
        saveDataBeforeExit();
    }

    private static void loadInitialData() {
        bookStore.loadBooksFromFile("books.txt");
        discountManager.loadDiscounts("discounts.txt");
        orderManager.loadOrders("orders.txt");
        reviewManager.loadReviews("reviews.txt");
    }

    private static void saveDataBeforeExit() {
        bookStore.saveBooksToFile("books.txt");
        discountManager.saveDiscounts("discounts.txt");
        orderManager.saveOrders("orders.txt");
        reviewManager.saveReviews("reviews.txt");
    }

    private static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n=== CỬA HÀNG ===");
            System.out.println("1. Nhân viên");
            System.out.println("2. Khách hàng");
            System.out.println("9. Thoát chương trình");
            choice = InputValidator.getPositiveInt("Vui lòng chọn một tùy chọn: ");

            switch (choice) {
                case 1:
                    if (authenticateStaff(scanner)) {
                        System.out.println("Truy cập thành công!");
                        staffMenu(scanner, bookStore, discountManager);
                    } else {
                        System.out.println("Sai mật khẩu! Truy cập bị từ chối.");
                    }
                    break;

                case 2:
                    do {
                        System.out.print("Vui lòng nhập tên của bạn: ");
                        customerName = scanner.nextLine().trim();
                        if (customerName.isEmpty()) {
                            System.out.println("Tên không được để trống. Vui lòng nhập lại.");
                        }
                    } while (customerName.isEmpty()); // Lặp lại yêu cầu nếu tên bị bỏ trống
                    customerMenu(scanner, cart, bookStore, customerName); // Truyền customerName vào menu khách hàng
                    break;

                case 9:
                    System.out.println("Cảm ơn bạn đã đến cửa hàng.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 9);
        scanner.close();

    }

    private static boolean authenticateStaff(Scanner scanner) {
        String password;
        do {
            System.out.print("Nhập mật khẩu nhân viên: ");
            password = scanner.nextLine().trim();

            if (password.isEmpty()) {
                System.out.println("Mật khẩu không được để trống. Vui lòng nhập lại.");
            }
        } while (password.isEmpty());
        return STAFF_PASSWORD.equals(password);
    }

    private static void staffMenu(Scanner scanner, BookStore bookStore, DiscountManager discountManager) {
        int choice;
        do {
            System.out.println("\n=== NHÂN VIÊN ===");
            System.out.println("1. Quản lý kho sách");
            System.out.println("2. Quản lý mã giảm giá");
            System.out.println("3. Xem lịch sử mua hàng");
            System.out.println("4. Xem đánh giá cửa hàng");
            System.out.println("9. Quay lại cửa hàng");
            choice = InputValidator.getPositiveInt("Vui lòng chọn một tùy chọn: ");

            switch (choice) {
                case 1:
                    manageBookStore(scanner, bookStore);
                    break;

                case 2:
                    manageDiscountCodes(scanner, discountManager);
                    break;

                case 3:
                    orderManager.displayOrderHistory();
                    break;

                case 4:
                    reviewManager.displayReviews();
                    break;

                case 9:
                    System.out.println("Quay lại menu cửa hàng.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 9);
    }

    private static void customerMenu(Scanner scanner, Cart cart, BookStore bookStore, String customerName) {
        int choice;
        do {
            System.out.println("\n=== KHÁCH HÀNG ===");
            System.out.println("1. Giỏ hàng");
            System.out.println("2. Thanh toán");
            System.out.println("3. Đánh giá cửa hàng");
            System.out.println("9. Quay lại cửa hàng");
            choice = InputValidator.getPositiveInt("Vui lòng chọn một tùy chọn: ");

            switch (choice) {
                case 1:
                    manageCart(scanner, cart, bookStore);
                    break;

                case 2:
                    checkout(scanner, cart, discountManager, customerName);
                    break;

                case 3:
                    reviewManager.rateStore(scanner, customerName);
                    ;
                    break;

                case 9:
                    System.out.println("Quay lại menu cửa hàng.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 9);
    }

    // CRUD cho kho sách
    private static void manageBookStore(Scanner scanner, BookStore bookStore) {
        int choice;
        do {
            System.out.println("\n=== QUẢN LÝ KHO SÁCH ===");
            System.out.println("1. Thêm sách vào kho");
            System.out.println("2. Hiển thị sách trong kho");
            System.out.println("3. Cập nhật sách");
            System.out.println("4. Xóa sách");
            System.out.println("9. Quay lại menu nhân viên");
            choice = InputValidator.getPositiveInt("Vui lòng chọn một tùy chọn: ");

            switch (choice) {
                case 1:
                    String productId = InputValidator.getStringInput("Nhập mã sách: ");
                    String name = InputValidator.getStringInput("Nhập tên sách: ");
                    String author = InputValidator.getStringInput("Nhập tên tác giả: ");
                    double price = InputValidator.getPositiveDouble("Nhập giá sách: ");
                    int quantity = InputValidator.getPositiveInt("Nhập số lượng sách: ");
                    bookStore.addBook(new Book(productId, name, author, price, quantity));
                    break;

                case 2:
                    bookStore.listBooks();
                    break;

                case 3:
                    productId = InputValidator.getStringInput("Nhập mã sách cần cập nhật: ");
                    quantity = InputValidator.getPositiveInt("Nhập số lượng mới: ");
                    price = InputValidator.getPositiveDouble("Nhập giá mới: ");
                    bookStore.updateBook(productId, quantity, price);
                    break;

                case 4:
                    productId = InputValidator.getStringInput("Nhập mã sách cần xóa: ");
                    bookStore.deleteBook(productId);
                    break;

                case 9:
                    System.out.println("Quay lại menu nhân viên.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 9);
    }

    // CRUD cho mã giảm giá
    private static void manageDiscountCodes(Scanner scanner, DiscountManager discountManager) {
        int choice;
        do {
            System.out.println("\n=== QUẢN LÝ MÃ GIẢM GIÁ ===");
            System.out.println("1. Thêm mã giảm giá");
            System.out.println("2. Hiển thị mã giảm giá");
            System.out.println("3. Cập nhật mã giảm giá");
            System.out.println("4. Xóa mã giảm giá");
            System.out.println("9. Quay lại menu nhân viên");
            choice = InputValidator.getPositiveInt("Vui lòng chọn một tùy chọn: ");

            switch (choice) {
                case 1:
                    String code = InputValidator.getStringInput("Nhập mã giảm giá: ");
                    double percentage = InputValidator.getPositiveDouble("Nhập phần trăm giảm giá: ");
                    int quantity = InputValidator.getPositiveInt("Nhập số lượng: ");
                    discountManager.addDiscountCode(new DiscountCode(code, percentage, quantity));
                    break;

                case 2:
                    discountManager.listDiscountCodes();
                    break;

                case 3:
                    code = InputValidator.getStringInput("Nhập mã giảm giá cần cập nhật: ");
                    percentage = InputValidator.getPositiveDouble("Nhập phần trăm giảm giá cần cập nhật: ");
                    quantity = InputValidator.getPositiveInt("Nhập số lượng cần cập nhật: ");
                    discountManager.updateDiscountCode(code, percentage, quantity);
                    break;

                case 4:
                    code = InputValidator.getStringInput("Nhập mã giảm giá cần xóa: ");
                    discountManager.deleteDiscountCode(code);
                    break;

                case 9:
                    System.out.println("Quay lại menu nhân viên.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 9);
    }

    // CRUD cho giỏ hàng
    private static void manageCart(Scanner scanner, Cart cart, BookStore bookStore) {
        int choice;
        do {
            System.out.println("\n=== GIỎ HÀNG ===");
            System.out.println("1. Hiển thị sách có ở cửa hàng");
            System.out.println("2. Thêm sách vào giỏ hàng");
            System.out.println("3. Hiển thị giỏ hàng");
            System.out.println("4. Cập nhật số lượng sách trong giỏ hàng");
            System.out.println("5. Xóa sách khỏi giỏ hàng");
            System.out.println("9. Quay lại menu khách hàng");
            choice = InputValidator.getPositiveInt("Vui lòng chọn một tùy chọn: ");

            switch (choice) {
                case 1:
                    bookStore.listBooks();
                    break;
                case 2:
                    String productId = InputValidator.getStringInput("Nhập mã sách: ");
                    Book book = bookStore.findBookById(productId);
                    if (book != null) {
                        int quantity = InputValidator.getPositiveInt("Nhập số lượng muốn thêm: ");
                        cart.addBookToCart(book, quantity);
                    } else {
                        System.out.println("Không tìm thấy sách với mã: " + productId);
                    }
                    break;

                case 3:
                    cart.listCartItems();
                    break;

                case 4:
                    if (cart.isEmpty()) {
                        System.out.println("Giỏ hàng trống. Không thể cập nhật.");
                        return;
                    }
                    productId = InputValidator.getStringInput("Nhập mã sách cần cập nhật: ");
                    book = cart.getBookInCart(productId);
                    if (book != null) {
                        int quantity = InputValidator.getPositiveInt("Nhập số lượng mới: ");
                        cart.updateBookQuantity(book, quantity);
                    } else {
                        System.out.println("Sách không có trong giỏ hàng.");
                    }
                    break;

                case 5:
                    if (cart.isEmpty()) {
                        System.out.println("Giỏ hàng trống. Không thể xóa sách.");
                        return;
                    }
                    productId = InputValidator.getStringInput("Nhập mã sách cần xóa khỏi giỏ hàng: ");
                    book = cart.getBookInCart(productId);
                    if (book != null) {
                        cart.removeBookFromCart(book);
                    } else {
                        System.out.println("Sách không có trong giỏ hàng.");
                    }
                    break;

                case 9:
                    System.out.println("Quay lại menu khách hàng.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 9);
    }

    // Thanh toán và lưu vào lịch sử
    private static void checkout(Scanner scanner, Cart cart, DiscountManager discountManager, String customerName) {
        if (cart.isEmpty()) {
            System.out.println("Giỏ hàng trống. Không thể thanh toán.");
            return;
        }

        double total = cart.calculateTotal();
        System.out.println("\n--- HÓA ĐƠN ---");
        System.out.println("Tên khách hàng: " + customerName);
        cart.listCartItems();
        String totalInfo = String.format("\nTổng tiền: %.0f VND", total);
        System.out.println(totalInfo);
        // Kiểm tra giảm giá theo số lượng sách mua
        int totalBooks = cart.getTotalQuantity();
        double comboDiscount = cart.calculateComboDiscount();
        if (comboDiscount > 0) {
            double discountAmount = total * comboDiscount;
            total -= discountAmount;
            String comboInfo = String.format("\nĐã áp dụng giảm giá combo %.0f%%: - %.0f VND",
                    comboDiscount * 100,
                    discountAmount
            );
            System.out.println(comboInfo);
        }

        // Áp dụng mã giảm giá (nếu có)
        boolean discountApplied = false;
        String appliedDiscountCode = null;
        while (!discountApplied) {
            System.out.print("\nNhập mã giảm giá (nếu có, hoặc bỏ qua): ");
            String code = scanner.nextLine().trim();
            if (code.isEmpty()) {
                System.out.println("Không áp dụng mã giảm giá.");
                break;
            }
            DiscountCode discount = discountManager.findDiscountCode(code);
            if (discount != null) {
                String applyDiscount;
                while (true) {
                    //if (discount != null) {
                    System.out.print("Bạn có muốn áp dụng mã giảm giá không? (Y/N): ");
                    applyDiscount = scanner.nextLine().trim();
                    if (applyDiscount.equalsIgnoreCase("Y") || applyDiscount.equalsIgnoreCase("N")) {
                        break;
                    } else {
                        System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập 'Y' hoặc 'N'.\n");
                    }
                }

                if (applyDiscount.equalsIgnoreCase("Y")) {
                    double discountAmount = total * discount.getDiscountPercentage();
                    total -= discountAmount;
                    String discountInfo = String.format("\nĐã áp dụng mã giảm giá %.0f%%: - %.0f VND",
                            discount.getDiscountPercentage() * 100,
                            discountAmount
                    );
                    System.out.println(discountInfo);
                    discountApplied = true;
                    appliedDiscountCode = code;

                } else {
                    System.out.println("\nMã giảm giá không được áp dụng.\n");
                    break;
                }
            } else {
                System.out.println("Mã giảm giá không hợp lệ. Vui lòng thử lại.");
            }
        }

        // Hiển thị tổng tiền sau tất cả các giảm giá
        if (discountApplied) {
            System.out.printf("\nTổng tiền sau giảm giá: %.0f VND\n\n", total);
        }

        // Xác nhận thanh toán
        String confirm;
        while (true) {
            System.out.print("Xác nhận thanh toán? (Y/N): ");
            confirm = scanner.nextLine().trim();
            if (confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("N")) {
                break;
            } else {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập 'Y' hoặc 'N'.\n");
            }
        }
        if (confirm.equalsIgnoreCase("Y")) {
            orderHistory.add(new Order(customerName, new HashMap<>(cart.getCartItems()), total));
            cart.clearCart();
            System.out.println("\nThanh toán thành công.");
            allowReview = true;
            if (appliedDiscountCode != null) {
                discountManager.removeDiscountCode(appliedDiscountCode);
            }
        } else {
            System.out.println("\nThanh toán đã bị hủy.");
            allowReview = false;
        }
    }
}
