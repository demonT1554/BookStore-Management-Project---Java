/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr01_assignment.app;

import gr01_assignment.models.Book;
import gr01_assignment.models.DiscountCode;
import gr01_assignment.models.Order;
import gr01_assignment.models.Review;
import gr01_assignment.services.BookStore;
import gr01_assignment.services.Cart;
import gr01_assignment.services.DiscountManager;
import gr01_assignment.services.OrderManager;
import gr01_assignment.services.ReviewManager;
import gr01_assignment.utils.InputValidator;
import gr01_assignment.utils.Password;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Le Anh Tuan - CE180905
 */
public class GR01_Assignment {

    private static final Cart cart = new Cart();
    private static String OWNER_PASSWORD;
    private static String STAFF_PASSWORD;
    private static final List<Review> storeReviews = new ArrayList<>();
    private static final List<Order> orderHistory = new ArrayList<>();
    private static boolean allowReview = false;
    private static String customerName;
    private static final DiscountManager discountManager = new DiscountManager();
    private static final OrderManager orderManager = new OrderManager();
    private static final BookStore bookStore = new BookStore();
    private static final ReviewManager reviewManager = new ReviewManager();
    private static final Password passwordManager = new Password();

    public static void main(String[] args) {
        passwordManager.loadPasswords("password.txt");
        OWNER_PASSWORD = passwordManager.getOwnerPassword();
        STAFF_PASSWORD = passwordManager.getStaffPassword();

        loadInitialData();
        displayMenu();
        saveDataBeforeExit();
    }

    private static void loadInitialData() {
        bookStore.loadBooksFromFile("books.txt");
        discountManager.loadDiscountsFromFile("discounts.txt");
        orderManager.loadOrdersFromFile("orders.txt");
        reviewManager.loadReviewsFromFile("reviews.txt");
    }

    private static void saveDataBeforeExit() {
        passwordManager.setOwnerPassword(OWNER_PASSWORD);
        passwordManager.setStaffPassword(STAFF_PASSWORD);
        passwordManager.savePasswords("password.txt");

        bookStore.saveBooksToFile("books.txt");
        discountManager.saveDiscountsToFile("discounts.txt");
        orderManager.saveOrdersToFile("orders.txt");
        reviewManager.saveReviewsToFile("reviews.txt");
    }

    private static void displayMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            int choice;
            do {
                System.out.println("\n=== CỬA HÀNG ===");
                System.out.println("1. Khách hàng");
                System.out.println("2. Nhân viên");
                System.out.println("3. Quản lý");
                System.out.println("9. Thoát chương trình");
                choice = InputValidator.getPositiveInt("Vui lòng chọn một tùy chọn: ");

                switch (choice) {
                    case 3:
                        if (authenticateOwner(scanner)) {
                            System.out.println("Truy cập thành công!");
                            ownerMenu(scanner, bookStore, discountManager);
                        } else {
                            System.out.println("Sai mật khẩu! Truy cập bị từ chối.");
                        }
                        break;
                    case 2:
                        if (authenticateStaff(scanner)) {
                            System.out.println("Truy cập thành công!");
                            staffMenu(scanner, bookStore, discountManager);
                        } else {
                            System.out.println("Sai mật khẩu! Truy cập bị từ chối.");
                        }
                        break;

                    case 1:
                        customerName = InputValidator.getNonEmptyString("Vui lòng nhập tên của bạn: ");
                        customerMenu(scanner, cart, bookStore, customerName);
                        break;

                    case 9:
                        System.out.println("Cảm ơn bạn đã đến cửa hàng.");
                        break;

                    default:
                        System.out.println("Lựa chọn không hợp lệ.");
                }
            } while (choice != 9);
        }

    }

    private static boolean authenticateOwner(Scanner scanner) {
        String password = InputValidator.getNonEmptyString("Nhập mật khẩu quản lý: ");
        return OWNER_PASSWORD.equals(password);
    }

    private static boolean authenticateStaff(Scanner scanner) {
        String password = InputValidator.getNonEmptyString("Nhập mật khẩu nhân viên: ");
        return STAFF_PASSWORD.equals(password);
    }

    private static void ownerMenu(Scanner scanner, BookStore bookStore1, DiscountManager discountManager1) {
        int choice;
        do {
            System.out.println("\n=== QUẢN LÝ ===");
            System.out.println("1. Quản lý kho sách");
            System.out.println("2. Quản lý mã giảm giá");
            System.out.println("3. Xem và quản lý tất cả các đơn hàng");
            System.out.println("4. Xem và quản lý đánh giá cửa hàng");
            System.out.println("5. Đổi mật khẩu nhân viên");
            System.out.println("6. Đổi mật khẩu quản lý");
            System.out.println("9. Quay lại");
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
                    manageReviews(scanner);
                    break;
                case 5:
                    changeStaffPassword(scanner);
                    break;
                case 6:
                    changeOwnerPassword(scanner);
                    break;
                case 9:
                    //System.out.println("Quay lại.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 9);
    }

    private static void changeStaffPassword(Scanner scanner) {
        STAFF_PASSWORD = InputValidator.getNonEmptyString("Nhập mật khẩu mới cho nhân viên: ");
        System.out.println("Mật khẩu nhân viên đã được đổi thành công.");
    }

    private static void changeOwnerPassword(Scanner scanner) {
        OWNER_PASSWORD = InputValidator.getNonEmptyString("Nhập mật khẩu mới cho quản lý: ");
        System.out.println("Mật khẩu quản lý đã được đổi thành công.");
    }

    private static void manageReviews(Scanner scanner) {
        int choice;
        do {
            System.out.println("\n=== QUẢN LÝ ĐÁNH GIÁ ===");
            System.out.println("1. Hiển thị tất cả đánh giá");
            System.out.println("2. Xóa đánh giá");
            System.out.println("3. Cập nhật đánh giá");
            System.out.println("9. Quay lại");
            choice = InputValidator.getPositiveInt("Vui lòng chọn một tùy chọn: ");

            switch (choice) {
                case 1:
                    reviewManager.displayReviews();
                    break;
                case 2:
                    String reviewerName = InputValidator.getStringInput("Nhập tên người đánh giá cần xóa: ");
                    reviewManager.removeReviewByReviewer(reviewerName);
                    break;
                case 3:
                    reviewerName = InputValidator.getStringInput("Nhập tên người đánh giá cần cập nhật: ");
                    int newRating = InputValidator.getPositiveIntRange("Nhập đánh giá mới (1 đến 5): ");
                    String newComment = InputValidator.getStringInput("Nhập bình luận mới: ");
                    reviewManager.updateReview(reviewerName, newRating, newComment);
                    break;
                case 9:
                    //System.out.println("Quay lại menu chủ quán.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 9);
    }

    private static void staffMenu(Scanner scanner, BookStore bookStore, DiscountManager discountManager) {
        int choice;
        do {
            System.out.println("\n=== NHÂN VIÊN ===");
            System.out.println("1. Quản lý kho sách");
            System.out.println("2. Quản lý mã giảm giá");
            System.out.println("3. Xem lịch sử mua hàng");
            System.out.println("4. Xem đánh giá cửa hàng");
            System.out.println("9. Quay lại");
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
                    //System.out.println("Quay lại menu cửa hàng.");
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
            System.out.println("9. Quay lại");
            choice = InputValidator.getPositiveInt("Vui lòng chọn một tùy chọn: ");

            switch (choice) {
                case 1:
                    manageCart(scanner, cart, bookStore);
                    break;

                case 2:
                    checkout(scanner, cart, discountManager, customerName);
                    break;

                case 3:
                    handleCustomerReview(scanner, customerName);
                    break;

                case 9:
                    //System.out.println("Quay lại menu cửa hàng.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 9);
    }

    private static void handleCustomerReview(Scanner scanner, String reviewerName) {
        System.out.println("\n--- ĐÁNH GIÁ CỦA KHÁCH HÀNG ---");
        int choice;
        do {
            System.out.println("1. Thêm đánh giá mới");
            System.out.println("2. Cập nhật đánh giá của bạn");
            System.out.println("9. Quay lại");
            choice = InputValidator.getPositiveInt("Vui lòng chọn một tùy chọn: ");

            switch (choice) {
                case 1:
                    reviewManager.addReviewFromCustomer(scanner, reviewerName);
                    break;
                case 2:
                    int newRating = InputValidator.getPositiveIntRange("Nhập đánh giá mới (1 đến 5): ");
                    String newComment = InputValidator.getStringInput("Nhập bình luận mới: ");
                    reviewManager.updateReview(reviewerName, newRating, newComment);
                    break;
                case 9:
                    //System.out.println("Quay lại menu khách hàng.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 9);
    }

    private static void manageBookStore(Scanner scanner, BookStore bookStore) {
        int choice;
        do {
            System.out.println("\n=== QUẢN LÝ KHO SÁCH ===");
            System.out.println("1. Thêm sách vào kho");
            System.out.println("2. Hiển thị sách trong kho");
            System.out.println("3. Cập nhật sách");
            System.out.println("4. Xóa sách");
            System.out.println("9. Quay lại");
            choice = InputValidator.getPositiveInt("Vui lòng chọn một tùy chọn: ");

            switch (choice) {
                case 1:
                    String productId = InputValidator.getStringInput("Nhập mã sách: ");
                    String name = InputValidator.getStringInput("Nhập tên sách: ");
                    String author = InputValidator.getStringInput("Nhập tên tác giả: ");
                    int price = InputValidator.getPositiveInt("Nhập giá sách: ");
                    int quantity = InputValidator.getPositiveInt("Nhập số lượng sách: ");
                    bookStore.addBookToInventory(new Book(productId, name, author, price, quantity));
                    bookStore.saveBooksToFile("books.txt");
                    break;

                case 2:
                    bookStore.listBooks();
                    break;

                case 3:
                    productId = InputValidator.getStringInput("Nhập mã sách cần cập nhật: ");
                    quantity = InputValidator.getPositiveInt("Nhập số lượng mới: ");
                    price = InputValidator.getPositiveInt("Nhập giá mới: ");
                    bookStore.updateBookInInventory(productId, quantity, price);
                    bookStore.saveBooksToFile("books.txt");
                    break;

                case 4:
                    productId = InputValidator.getStringInput("Nhập mã sách cần xóa: ");
                    int quantityToRemove = InputValidator.getPositiveInt("Nhập số lượng sách cần xóa: ");
                    bookStore.removeBookFromInventory(productId, quantityToRemove);
                    bookStore.saveBooksToFile("books.txt");
                    break;

                case 9:
                    //System.out.println("Quay lại menu nhân viên.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 9);
    }

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
                    int percentage = InputValidator.getPositiveInt("Nhập phần trăm giảm giá: ");
                    int quantity = InputValidator.getPositiveInt("Nhập số lượng: ");
                    discountManager.addDiscountCode(new DiscountCode(code, percentage, quantity));
                    discountManager.saveDiscountsToFile("discounts.txt");
                    break;

                case 2:
                    discountManager.listDiscountCodes();
                    break;

                case 3:
                    code = InputValidator.getStringInput("Nhập mã giảm giá cần cập nhật: ");
                    percentage = InputValidator.getPositiveInt("Nhập phần trăm giảm giá cần cập nhật: ");
                    quantity = InputValidator.getPositiveInt("Nhập số lượng cần cập nhật: ");
                    discountManager.updateDiscountCode(code, percentage, quantity);
                    discountManager.saveDiscountsToFile("discounts.txt");
                    break;

                case 4:
                    code = InputValidator.getStringInput("Nhập mã giảm giá cần xóa: ");
                    int quantityToRemove = InputValidator.getPositiveInt("Nhập số lượng mã giảm giá cần xóa: ");
                    discountManager.deleteDiscountCode(code, quantityToRemove);
                    break;

                case 9:
                    //System.out.println("Quay lại menu nhân viên.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 9);
    }

    private static void manageCart(Scanner scanner, Cart cart, BookStore bookStore) {
        int choice;
        do {
            System.out.println("\n=== GIỎ HÀNG ===");
            System.out.println("1. Hiển thị sách có ở cửa hàng");
            System.out.println("2. Thêm sách vào giỏ hàng");
            System.out.println("3. Hiển thị giỏ hàng");
            System.out.println("4. Cập nhật số lượng sách trong giỏ hàng");
            System.out.println("5. Xóa sách khỏi giỏ hàng");
            System.out.println("9. Quay lại");
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
                    //System.out.println("Quay lại menu khách hàng.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 9);
    }

    private static void checkout(Scanner scanner, Cart cart, DiscountManager discountManager, String customerName) {
        if (cart.isEmpty()) {
            System.out.println("Giỏ hàng trống. Không thể thanh toán.");
            return;
        }
        int total = cart.calculateTotal();
        System.out.println("\n--- HÓA ĐƠN ---");
        System.out.println("Tên khách hàng: " + customerName);
        cart.listCartItems();
        String totalInfo = String.format("\nTổng tiền: %d VND", total);
        System.out.println(totalInfo);

        int comboDiscount = cart.calculateComboDiscount();
        if (comboDiscount > 0) {
            int discountAmount = total * comboDiscount / 100;
            total -= discountAmount;
            String comboInfo = String.format("\nĐã áp dụng giảm giá combo %d%%: - %d VND",
                    comboDiscount,
                    discountAmount
            );
            System.out.println(comboInfo);
        }
        String code = InputValidator.getDiscountCode("Nhập mã giảm giá (nếu có, hoặc bỏ qua): ");
        String appliedDiscountCode = null;
        if (code != null) {
            DiscountCode discount = discountManager.findDiscountCode(code);
            if (discount != null) {
                boolean applyDiscount = InputValidator.getConfirmation("Bạn có muốn áp dụng mã giảm giá không? (Y/N): ");
                if (applyDiscount) {
                    int discountAmount = (int) (total * discount.getDiscountPercentage() / 100.0);
                    total -= discountAmount;
                    System.out.printf("\nĐã áp dụng mã giảm giá %d%%: -%d VND\n", discount.getDiscountPercentage(), discountAmount);
                    appliedDiscountCode = code;
                } else {
                    System.out.println("Mã giảm giá không được áp dụng.");
                }
            } else {
                System.out.println("Mã giảm giá không hợp lệ. Vui lòng thử lại.");
            }
        }

        boolean confirm = InputValidator.getConfirmation("Xác nhận thanh toán? (Y/N): ");
        if (confirm) {
            Order newOrder = new Order(customerName, new HashMap<>(cart.getCartItems()), total);
            orderManager.addOrder(newOrder, "orders.txt");

            for (Map.Entry<Book, Integer> entry : cart.getCartItems().entrySet()) {
                Book cartBook = entry.getKey();
                int quantityPurchased = entry.getValue();

                int newQuantity = cartBook.getQuantity() - quantityPurchased;
                bookStore.updateBookInInventory(cartBook.getProductId(), newQuantity, cartBook.getPrice());
            }

            cart.clearCart();
            System.out.println("\nThanh toán thành công.");
            allowReview = true;

            if (appliedDiscountCode != null) {
                discountManager.applyDiscountAndReduceQuantity(appliedDiscountCode);
                discountManager.saveDiscountsToFile("discounts.txt");
            }

            bookStore.saveBooksToFile("books.txt");
        } else {
            System.out.println("\nThanh toán đã bị hủy.");
            allowReview = false;
        }
    }
}
