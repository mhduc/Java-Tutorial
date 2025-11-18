# ☕ Quy Chuẩn Project Java Spring Boot

Đây là một bộ hướng dẫn nhằm thiết lập một cấu trúc dự án chuẩn và thống nhất, giúp việc phát triển và bảo trì các ứng dụng Spring Boot trở nên dễ dàng và hiệu quả hơn.

---

## 1. Cấu Trúc Thư Mục Project (Maven/Gradle)

Cấu trúc thư mục tiêu chuẩn của Spring Boot/Maven/Gradle nên được tuân thủ nghiêm ngặt.

src/
├── main/
│   ├── java/
│   │   └── com/yourcompany/yourapp/ (Root Package)
│   │       ├── config/        (Cấu hình ứng dụng: Security, Caching, etc.)
│   │       ├── controller/    (Các REST Controllers)
│   │       ├── service/       (Business Logic)
│   │       ├── repository/    (Spring Data JPA Repositories/DAO)
│   │       ├── model/         (Các lớp Entity/Domain Models)
│   │       ├── dto/           (Data Transfer Objects - cho giao tiếp API)
│   │       ├── exception/     (Các lớp ngoại lệ tùy chỉnh và Global Exception Handler)
│   │       └── YourAppApplication.java (Lớp khởi động chính)
│   └── resources/
│       ├── application.yml/properties (Cấu hình ứng dụng)
│       ├── static/          (Tài nguyên tĩnh: CSS, JS, Images)
│       ├── templates/       (Thymeleaf/JSP templates nếu có)
│       └── logback-spring.xml (Cấu hình logging)
└── test/
    └── java/
        └── com/yourcompany/yourapp/
            ├── controller/  (Unit/Integration Tests cho Controllers)
            ├── service/     (Unit Tests cho Services)
            └── repository/  (Unit/Integration Tests cho Repositories)

---

## 2. Quy Tắc Đặt Tên (Naming Conventions)

Sự nhất quán trong đặt tên là chìa khóa để dễ dàng đọc và bảo trì mã nguồn.

| Thành phần | Quy tắc đặt tên | Ví dụ |
| :--- | :--- | :--- |
| **Package** | `lowercase.separated.by.dots` | `com.app.core.service` |
| **Class/Interface** | `PascalCase` (Chữ cái đầu mỗi từ viết hoa) | `UserService`, `ProductController` |
| **Interface Implementations** | Thêm hậu tố `Impl` | `UserServiceImpl` |
| **Service** | Thêm hậu tố `Service` | `ProductService` |
| **Controller** | Thêm hậu tố `Controller` | `OrderController` |
| **Repository/DAO** | Thêm hậu tố `Repository` hoặc `Dao` | `UserRepository` |
| **Entity/Model** | Dùng danh từ số ít `PascalCase` | `User`, `Product` |
| **DTO** | Dùng danh từ số ít và thêm hậu tố `Dto` | `UserCreationDto`, `ProductResponseDto` |
| **Biến/Phương thức** | `camelCase` (Chữ cái đầu tiên viết thường) | `getUserById(Long id)`, `productName` |
| **Hằng số** | `UPPER_CASE_WITH_UNDERSCORES` | `DEFAULT_PAGE_SIZE` |

---

## 3. Nguyên Tắc Coding và Spring Boot

### 3.1. Phân Tầng (Layering)

* **Controller:** Xử lý yêu cầu HTTP, ủy thác logic cho Service. **Không chứa logic nghiệp vụ.**
* **Service:** Chứa **logic nghiệp vụ** (Business Logic) cốt lõi. Giao tiếp với Repository.
* **Repository:** Chịu trách nhiệm tương tác với cơ sở dữ liệu (CRUD operations).

### 3.2. Tiêm Phụ Thuộc (Dependency Injection - DI)

* **Ưu tiên Constructor Injection:** Thay vì `@Autowired` trên trường (Field Injection). Mọi dependency trong constructor nên là `final`.

    ```java
    @Service
    public class UserService {
        private final UserRepository userRepository;

        public UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }
        // ...
    }
    ```

### 3.3. DTO (Data Transfer Objects)

* Sử dụng DTO để chuyển đổi dữ liệu giữa các tầng (Controller và Service) nhằm **tách biệt mô hình Entity khỏi API** và ngăn chặn việc phơi bày các trường nhạy cảm.
* Tránh trả về trực tiếp các Entity từ Repository lên Controller.

### 3.4. REST API

* **Tuân thủ RESTful:**
    * Sử dụng **danh từ số nhiều** cho các endpoint (ví dụ: `/api/users`).
    * Sử dụng đúng động từ HTTP (`GET`, `POST`, `PUT/PATCH`, `DELETE`).
* **Response Status Codes:** Luôn trả về mã HTTP status code chính xác (ví dụ: `200 OK`, `201 Created`, `400 Bad Request`, `404 Not Found`).

### 3.5. Xử lý Ngoại lệ (Exception Handling)

* Sử dụng `@RestControllerAdvice` (hoặc `@ControllerAdvice`) và `@ExceptionHandler` để xây dựng **Global Exception Handler** tập trung, đảm bảo phản hồi lỗi JSON nhất quán.

---

## 4. Quy Tắc Cụ Thể cho Unit Testing

Tập trung vào việc tạo ra các bài kiểm thử đơn vị **nhanh, độc lập, và đáng tin cậy** cho ứng dụng.

### 4.1. Phân Loại Kiểm Thử

* **Unit Tests:** Kiểm thử các thành phần riêng lẻ (phương thức trong Service) **không cần khởi động Spring Context**. Thường sử dụng **Mocks (Mockito)**.
* **Integration Tests (IT):** Kiểm thử sự tương tác giữa các lớp (ví dụ: Controller -> Service -> Repository). Thường **khởi động một phần của Spring Context** (ví dụ: dùng `@WebMvcTest` hoặc `@DataJpaTest`).

### 4.2. Quy Tắc Đặt Tên Test

| Loại Test | Tên Class | Tên Phương thức |
| :--- | :--- | :--- |
| **Unit Test** | `UserServiceTest.java` | `[Tên phương thức]_[Điều kiện]_[Kết quả mong muốn]` |
| **Integration Test** | `ProductControllerIT.java` | Ví dụ: `getProductById_userFound_return200()` |

### 4.3. Testing Services (Sử dụng Mockito)

* **Approach:** Luôn **Mock** các **phụ thuộc** (Repositories, Client API) của Service đang kiểm thử.
* **Annotation:** Sử dụng `@ExtendWith(MockitoExtension.class)`, `@InjectMocks` cho lớp chính, và `@Mock` cho các phụ thuộc.
* **Verification:** Sử dụng `Mockito.when()` để định nghĩa hành vi trả về của phụ thuộc và `Mockito.verify()` để kiểm tra các lời gọi phương thức.

### 4.4. Testing Controllers

* **Approach:** Sử dụng `@WebMvcTest(Controller.class)` và **MockMvc** để kiểm thử HTTP request/response.
* **Dependencies:** Sử dụng `@MockBean` cho các Service mà Controller gọi đến để giả lập hành vi của Service Layer.