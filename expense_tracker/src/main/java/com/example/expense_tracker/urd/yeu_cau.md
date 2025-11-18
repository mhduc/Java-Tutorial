# ☕ Yêu Cầu Dự Án Ứng Dụng Theo Dõi Chi Tiêu (yeu_cau.md)

Đây là bộ yêu cầu chi tiết cho việc xây dựng một ứng dụng theo dõi chi tiêu đơn giản, giúp người dùng quản lý tài chính cá nhân. Ứng dụng chạy từ dòng lệnh (command line) và tập trung vào các tính năng cơ bản, cùng với một số tính năng bổ sung tùy chọn.

---

## 1. Giới Thiệu Ứng Dụng

Ứng dụng **Expense Tracker** cho phép người dùng quản lý các khoản chi tiêu hàng ngày. Các tính năng chính bao gồm thêm, cập nhật, xóa, xem danh sách chi tiêu, và tổng hợp thống kê. Ứng dụng được thiết kế đơn giản, dễ sử dụng qua giao diện dòng lệnh.

---

## 2. Yêu Cầu Chức Năng Cơ Bản

Ứng dụng **phải** hỗ trợ các tính năng sau qua giao diện dòng lệnh:

* **Thêm chi tiêu:** Người dùng có thể thêm một khoản chi tiêu với **mô tả (description)** và **số tiền (amount)**.
* **Cập nhật chi tiêu:** Người dùng có thể chỉnh sửa thông tin của một khoản chi tiêu hiện có (ví dụ: thay đổi mô tả hoặc số tiền).
* **Xóa chi tiêu:** Người dùng có thể xóa một khoản chi tiêu cụ thể.
* **Xem tất cả chi tiêu:** Hiển thị danh sách toàn bộ các khoản chi tiêu đã lưu.
* **Xem tổng hợp tất cả chi tiêu:** Cung cấp tổng kết (summary) về **tổng số tiền chi tiêu**.
* **Xem tổng hợp chi tiêu theo tháng:** Hiển thị tổng kết chi tiêu cho một **tháng cụ thể trong năm hiện tại** (ví dụ: nhập tháng để xem tổng tiền chi trong tháng đó).

---

## 3. Yêu Cầu Chức Năng Bổ Sung (Tùy Chọn)

Để nâng cao ứng dụng, có thể thêm các tính năng sau:

* **Phân loại chi tiêu theo danh mục:** Cho phép thêm **danh mục (categories)** cho chi tiêu và **lọc danh sách theo danh mục**.
* **Thiết lập ngân sách hàng tháng:** Người dùng có thể đặt **ngân sách cho từng tháng** và hiển thị **cảnh báo nếu tổng chi tiêu vượt quá ngân sách**.
* **Xuất dữ liệu ra file CSV:** Cho phép **xuất danh sách chi tiêu ra file CSV** để lưu trữ hoặc phân tích.

---

## 4. Yêu Cầu Kỹ Thuật

### 4.1. Nền Tảng Phát Triển

* **Ngôn ngữ:** Java (ưu tiên sử dụng **Spring Boot** nếu phù hợp cho ứng dụng command-line).
* **Giao diện:** Command-line interface (CLI), sử dụng `Scanner` hoặc thư viện tương tự để nhận input từ người dùng.
* **Lưu trữ dữ liệu:** Sử dụng cơ sở dữ liệu đơn giản như **H2 (in-memory)** hoặc file JSON/CSV để lưu chi tiêu. Tránh sử dụng cơ sở dữ liệu phức tạp trừ khi cần thiết.

### 4.2. Cấu Trúc Dự Án

Tuân thủ cấu trúc thư mục tiêu chuẩn (dựa trên Maven/Gradle):
src/
├── main/
│   ├── java/
│   │   └── com/yourcompany/expensetracker/ (Root Package)
│   │       ├── config/        (Cấu hình nếu cần)
│   │       ├── service/       (Logic xử lý chi tiêu)
│   │       ├── repository/    (Lưu trữ dữ liệu)
│   │       ├── model/         (Lớp Expense, Category, etc.)
│   │       ├── dto/           (Nếu cần DTO cho chuyển đổi dữ liệu)
│   │       ├── exception/     (Xử lý ngoại lệ)
│   │       └── ExpenseTrackerApp.java (Lớp khởi động chính)
│   └── resources/
│       ├── application.properties (Cấu hình nếu dùng Spring)
│       └── data/              (File lưu trữ nếu cần)
└── test/
└── java/
└── com/yourcompany/expensetracker/
├── service/     (Unit Tests cho Services)
└── repository/  (Unit/Integration Tests cho Repositories)


### 4.3. Quy Tắc Đặt Tên

Áp dụng các quy tắc đặt tên nhất quán:

| Thành phần | Quy tắc đặt tên | Ví dụ |
| :--- | :--- | :--- |
| **Class** | `PascalCase` | `ExpenseService`, `ExpenseRepository` |
| **Phương thức** | `camelCase` | `addExpense(String description, double amount)` |
| **Biến** | `camelCase` | `totalAmount` |
| **Hằng số** | `UPPER_CASE_WITH_UNDERSCORES` | `DEFAULT_BUDGET` |

### 4.4. Xử Lý Dữ Liệu

* Mỗi khoản chi tiêu nên có ít nhất: **ID**, **mô tả**, **số tiền**, **ngày tháng** (sử dụng `LocalDate`).
* Tổng hợp nên tính toán **tổng tiền**, có thể **nhóm theo tháng hoặc danh mục**.

### 4.5. Xử Lý Ngoại Lệ

* Xử lý các trường hợp lỗi như **input không hợp lệ**, **chi tiêu không tồn tại**, v.v., và hiển thị thông báo thân thiện.

---

## 5. Quy Tắc Kiểm Thử (Testing)

### 5.1. Phân Loại Kiểm Thử

* **Unit Tests:** Kiểm tra logic riêng lẻ (ví dụ: phương thức tính tổng trong Service).
* **Integration Tests:** Kiểm tra tích hợp giữa các lớp (ví dụ: Service và Repository).

### 5.2. Quy Tắc Đặt Tên Test

| Loại Test | Tên Class | Tên Phương thức |
| :--- | :--- | :--- |
| **Unit Test** | `ExpenseServiceTest.java` | `addExpense_validInput_addsSuccessfully` |
| **Integration Test** | `ExpenseRepositoryIT.java` | `saveExpense_dataPersisted_returnsExpense` |

### 5.3. Công Cụ Testing

* Sử dụng **JUnit** và **Mockito** để mock phụ thuộc.
* Ví dụ cho Service Test:

    ```java
    @ExtendWith(MockitoExtension.class)
    public class ExpenseServiceTest {
        @Mock
        private ExpenseRepository repository;
        @InjectMocks
        private ExpenseService service;

        @Test
        void addExpense_validInput_addsSuccessfully() {
            // Arrange
            Expense expense = new Expense("Food", 100.0);
            when(repository.save(any(Expense.class))).thenReturn(expense);

            // Act
            Expense result = service.addExpense("Food", 100.0);

            // Assert
            assertNotNull(result);
            verify(repository).save(any(Expense.class));
        }
    }