# 📋 Dự Án: Task Tracker CLI Đơn Giản

Dự án này là một ứng dụng giao diện dòng lệnh (**CLI**) cho phép người dùng theo dõi và quản lý các công việc (tasks) của mình. Mục tiêu là luyện tập các kỹ năng lập trình cơ bản như làm việc với **hệ thống tệp (filesystem)**, xử lý **đầu vào người dùng** và xây dựng một **ứng dụng CLI** đơn giản mà không cần thư viện ngoài.

---

## ✨ Yêu Cầu Chức Năng (Features)

Người dùng cần có khả năng thực hiện các hành động sau thông qua dòng lệnh:

* **Thêm (Add) Task:** Thêm một công việc mới vào danh sách. Công việc mới mặc định có trạng thái là **`todo`** (chưa làm).
* **Cập nhật (Update) Task:** Thay đổi mô tả của một công việc dựa trên ID của nó.
* **Xóa (Delete) Task:** Xóa một công việc khỏi danh sách dựa trên ID.
* **Đánh dấu Trạng thái:** Thay đổi trạng thái của một công việc:
    * Đánh dấu là **`in_progress`** (đang làm).
    * Đánh dấu là **`done`** (hoàn thành).
* **Liệt kê (List) Task:** Hiển thị danh sách các công việc theo các tiêu chí khác nhau:
    * Liệt kê **tất cả** các công việc.
    * Liệt kê các công việc **đã hoàn thành** (`done`).
    * Liệt kê các công việc **chưa hoàn thành** (`todo`).
    * Liệt kê các công việc **đang thực hiện** (`in_progress`).

---

## 🛠️ Ràng Buộc Kỹ Thuật (Constraints)

Để đảm bảo tính thử thách và tập trung vào các kiến thức cốt lõi, dự án phải tuân thủ các ràng buộc sau:

1.  **Ngôn ngữ Lập trình:** Có thể sử dụng **bất kỳ ngôn ngữ lập trình** nào.
2.  **Đầu vào CLI:** Sử dụng **đối số vị trí (positional arguments)** để nhận hành động và dữ liệu đầu vào từ người dùng.
3.  **Lưu trữ Dữ liệu:**
    * Sử dụng một tệp **JSON** để lưu trữ tất cả các công việc.
    * Tệp JSON này phải được tạo tự động trong **thư mục hiện tại** nếu nó chưa tồn tại.
    * Tên tệp khuyến nghị: **`tasks.json`**.
4.  **Tương tác Tệp:** Chỉ sử dụng các **module/thư viện tích hợp sẵn (native file system module)** của ngôn ngữ lập trình để thao tác với tệp JSON.
5.  **Không Thư viện Ngoài:** **Không được sử dụng bất kỳ thư viện, framework hay gói bên ngoài** nào để xây dựng ứng dụng (ví dụ: không dùng thư viện quản lý CLI như `Click` hay `Typer` trong Python).
6.  **Xử lý Lỗi:** Phải xử lý lỗi và các trường hợp biên (**edge cases**) một cách trơn tru (ví dụ: ID không tồn tại, cú pháp lệnh sai, tệp JSON bị hỏng).

---

## 📂 Cấu Trúc Dữ liệu Task

Mỗi công việc (task) sẽ là một đối tượng trong JSON có cấu trúc như sau:

| Khóa (Key) | Kiểu Dữ liệu (Type) | Mô tả |
| :--- | :--- | :--- |
| **`id`** | Số nguyên (Integer) | Mã định danh duy nhất của công việc. |
| **`description`** | Chuỗi (String) | Nội dung chi tiết của công việc. |
| **`status`** | Chuỗi (String) | Trạng thái hiện tại: `todo`, `in_progress`, hoặc `done`. |

**Ví dụ nội dung `tasks.json`:**

```json
[
  {
    "id": 1,
    "description": "Viết tài liệu hướng dẫn cho CLI.",
    "status": "in_progress"
  },
  {
    "id": 2,
    "description": "Hoàn thành bài tập lập trình tuần này.",
    "status": "todo"
  }
]