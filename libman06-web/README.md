# LibMan06 – Hướng dẫn cài và chạy từ đầu

Ứng dụng web Java (JSP + Servlet + JDBC + MySQL) cho 2 module của đề 02:

- **Bạn đọc đăng ký thẻ bạn đọc**
- **Bạn đọc trả tài liệu** (nhân viên thư viện thao tác)

Làm lần lượt từ bước 1 đến bước 6 là chạy được trên một máy mới. Máy đã cài sẵn mọi thứ thì nhảy tới [bước 6](#6-chạy-ứng-dụng).

| Bước | Việc | Thời gian |
|---|---|---|
| 1 | Cài môi trường (JDK, MySQL, Git) | 15–30 phút, chỉ làm một lần |
| 2 | Lấy code về | 1 phút |
| 3 | Cấu hình kết nối CSDL | 1 phút |
| 4 | Chạy migration (tạo database + dữ liệu demo) | 1 phút |
| 5 | Kiểm tra database | 1 phút |
| 6 | Chạy ứng dụng | lần đầu 1–2 phút, các lần sau vài giây |

---

## 1. Cài môi trường

### Cần có

| Phần mềm | Phiên bản | Để làm gì | Bắt buộc? |
|---|---|---|---|
| **JDK** (Java Development Kit) | **17 trở lên** (khuyên dùng 21) | Biên dịch và chạy code Java | Có |
| **MySQL Server** | **8.0** | Lưu dữ liệu | Có |
| **Git** (kèm Git Bash) | bất kỳ | Lấy code từ GitHub, chạy `run-demo.sh` trên Windows | Có trên Windows (dùng Git Bash) |
| MySQL Workbench | 8.0 | Xem dữ liệu, chạy SQL bằng giao diện | Không |
| VS Code + *Extension Pack for Java* | bất kỳ | Đọc code, bấm Run | Không |
| Trình duyệt (Chrome, Edge, ...) | bất kỳ | Mở web | Có |

**Không cần cài** Maven và Tomcat:

- Maven: project có sẵn Maven Wrapper (`mvnw.cmd`). Lần build đầu tiên nó tự tải Maven và các thư viện, nên **cần có Internet** lần đầu.
- Tomcat: ứng dụng chạy bằng Tomcat nhúng (embedded), đã có trong thư viện.

Cổng mạng cần trống: **8080** (web) và **3306** (MySQL).

### 1.1 Cài JDK

1. Tải **Eclipse Temurin JDK 21** (bản `.msi` cho Windows x64) tại <https://adoptium.net/>.
2. Khi cài, tick 2 tùy chọn **Set JAVA_HOME variable** và **Add to PATH**.
3. Mở **cửa sổ terminal mới**, kiểm tra:

   ```
   java -version
   ```

   Thấy `openjdk version "21..."` (hoặc 17 trở lên) là được.

> Máy có Android Studio thì không cài cũng được: `run-demo.sh` tự dùng JDK đi kèm Android Studio (`C:\Program Files\Android\Android Studio\jbr`).

### 1.2 Cài MySQL Server 8.0

1. Tải **MySQL Installer for Windows** tại <https://dev.mysql.com/downloads/installer/>.
2. Chọn cài **MySQL Server 8.0** (thêm **MySQL Workbench** nếu muốn).
3. Ở bước *Accounts and Roles*, đặt **mật khẩu cho root**. Nhớ mật khẩu này, bước 3 sẽ dùng.
4. Ở bước *Windows Service*, để mặc định: tên service `MySQL80`, tự chạy khi bật máy.
5. Kiểm tra MySQL đang chạy: mở **Services** (`Win + R` → `services.msc`), tìm `MySQL80`, cột Status phải là **Running**.

### 1.3 Cài Git

Tải tại <https://git-scm.com/download/win>, cài mặc định. Bộ cài có kèm **Git Bash**, cần để chạy `run-demo.sh` trên Windows.

---

## 2. Lấy code về

Bằng Git:

```
git clone https://github.com/hachang0a0/pttkhttt.git
cd pttkhttt\libman06-web
```

Không có Git: trên trang GitHub bấm **Code → Download ZIP**, giải nén, rồi mở thư mục `libman06-web`.

Từ đây trở đi, mọi lệnh đều chạy **trong thư mục `libman06-web`**.

---

## 3. Cấu hình kết nối CSDL

Ứng dụng đọc thông tin kết nối từ file `src/main/resources/db.properties`. File này chứa mật khẩu nên **không có trên GitHub**, bạn tự tạo:

1. Copy `src/main/resources/db.properties.example` thành `src/main/resources/db.properties` (cùng thư mục).
2. Mở `db.properties`, điền mật khẩu root của MySQL vào dòng cuối:

   ```properties
   db.url=jdbc:mysql://localhost:3306/libman06?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Ho_Chi_Minh
   db.user=root
   db.password=mat_khau_cua_ban
   ```

   MySQL chạy ở cổng khác 3306 hoặc ở máy khác thì sửa `localhost:3306` trong `db.url`.

---

## 4. Chạy migration (tạo database và dữ liệu demo)

Migration là file **`database/libman06.sql`**. Nó làm 3 việc:

1. **Xóa database `libman06` nếu đã có**, rồi tạo lại (bảng mã `utf8mb4`, gõ tiếng Việt được).
2. Tạo 7 bảng: `reader`, `reader_card`, `staff`, `document`, `loan_slip`, `loan_detail`, `return_invoice`.
3. Nạp dữ liệu demo đúng kịch bản (xem [bước 6](#tài-khoản-và-dữ-liệu-demo)).

> ⚠️ Chạy lại script là **xóa hết dữ liệu cũ** và đưa về dữ liệu demo. Dùng chính việc này để reset trước mỗi lần demo.

Chọn **một** trong 3 cách:

### Cách A – `setup-db.sh` (một lệnh, dễ nhất)

Trong thư mục `libman06-web` (Windows thì mở **Git Bash**), chạy:

```bash
./setup-db.sh
```

Thấy `Database libman06 is ready.` là xong.

- Script lấy user và mật khẩu từ `db.properties` (bước 3), nên **không phải gõ mật khẩu**. Chưa có file đó thì script hỏi mật khẩu.
- Dùng user MySQL khác: `./setup-db.sh ten_user` (script sẽ hỏi mật khẩu của user đó).
- Script tự tìm `mysql` trong PATH; trên Windows không có thì dùng `C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe`.

### Cách B – Dòng lệnh

```
mysql -u root -p --default-character-set=utf8mb4 < database\libman06.sql
```

Nếu báo `'mysql' is not recognized`, gọi bằng đường dẫn đầy đủ:

```
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p --default-character-set=utf8mb4 < database\libman06.sql
```

### Cách C – MySQL Workbench

1. Mở Workbench, kết nối vào `Local instance MySQL80` bằng root.
2. **File → Open SQL Script...** → chọn `database/libman06.sql`.
3. Bấm nút tia sét ⚡ (**Execute**). Đợi chạy xong, không có dòng đỏ là được.

---

## 5. Kiểm tra database

Chạy trong Workbench hoặc `mysql -u root -p`:

```sql
USE libman06;
SELECT card_code, full_name FROM reader_card JOIN reader ON reader.id = reader_card.reader_id;
SELECT COUNT(*) FROM loan_detail WHERE status = 'BORROWING';
```

Đúng thì thấy 6 thẻ `RC000001` → `RC000006` (`RC000006` là Nguyễn Văn A) và 4 dòng đang mượn.

---

## 6. Chạy ứng dụng

### Cách A – `run-demo.sh` (một lệnh)

Mở terminal ở thư mục `libman06-web` rồi chạy:

```bash
./run-demo.sh
```

- **Windows:** chạy trong **Git Bash** (có sẵn khi cài Git, bước 1.3), hoặc chọn terminal *Git Bash* trong VS Code. PowerShell và cmd không chạy được file `.sh`.
- **macOS / Linux:** chạy trong terminal bình thường. Báo `Permission denied` thì chạy `chmod +x run-demo.sh mvnw` một lần.

Script tự tìm Java (`JAVA_HOME` → JDK của Android Studio → `java` trong PATH), tự build bằng Maven Wrapper rồi khởi động Tomcat nhúng. Đợi đến khi thấy:

```
LibMan06 is running:  http://localhost:8080/
Staff login:          hoangva / 123456
Press Ctrl+C to stop.
```

Mở trình duyệt vào **<http://localhost:8080/>**. Tắt server: bấm `Ctrl + C` trong cửa sổ đó (hoặc đóng cửa sổ).

Cổng 8080 bị chiếm thì chọn cổng khác: `./run-demo.sh 8081` → mở `http://localhost:8081/`.

### Cách B – Gõ lệnh từng bước

Windows:

```
mvnw.cmd -DskipTests package
java -cp "target\classes;target\lib\*" libman06.LibManApp06 8080
```

macOS / Linux (migration chạy bằng `mysql -u root -p < database/libman06.sql`):

```
./mvnw -DskipTests package
java -cp "target/classes:target/lib/*" libman06.LibManApp06 8080
```

### Cách C – Bấm Run trong VS Code

1. Cài extension **Extension Pack for Java**, mở thư mục repo.
2. Build ít nhất một lần bằng cách A hoặc B (để có `target/lib`).
3. Mở `src/main/java/libman06/LibManApp06.java`, bấm **Run** phía trên hàm `main`.

### Cách D – Triển khai file WAR lên Tomcat thật

Dùng khi muốn chạy giống mô hình triển khai ở Câu 5b:

1. Cài **Apache Tomcat 10.1** (phải là bản 10.x vì code dùng `jakarta.servlet`).
2. Làm xong bước 3 (có `db.properties`), rồi chạy `mvnw.cmd -DskipTests package` → ra file `target/libman06.war`. File `db.properties` được đóng gói luôn vào WAR.
3. Copy `libman06.war` vào thư mục `webapps/` của Tomcat, chạy `bin/startup.bat`.
4. Mở **<http://localhost:8080/libman06/>**.

### Các trang chính

| Địa chỉ | Trang |
|---|---|
| `/` | Trang chủ bạn đọc |
| `/register-card` | Đăng ký thẻ bạn đọc (module 1) |
| `/login` | Nhân viên đăng nhập |
| `/staff/home` | Trang chủ nhân viên |
| `/staff/search-card` | Nhận trả tài liệu (module 2) |

### Tài khoản và dữ liệu demo

| Dữ liệu | Dùng để |
|---|---|
| Nhân viên `hoangva` / `123456` | Đăng nhập |
| Thẻ `RC000006` – Nguyễn Văn A, đang mượn 3 cuốn (muộn 14 ngày, muộn 5 ngày, chưa đến hạn) | Kịch bản chuẩn trả sách: tick 2 cuốn + phạt hư hỏng 20.000 → tổng **48.000đ** |
| Thẻ `RC000002` – chưa mượn gì | Ngoại lệ "không mượn tài liệu nào" |
| Mã `RC000060` | Ngoại lệ "không tìm thấy thẻ" |
| Mã SV `B21DCCN006` | Ngoại lệ "sinh viên đã có thẻ" khi đăng ký |
| Đăng ký mới | Được mã thẻ **RC000007** |

Ngày mượn được tính lùi từ ngày chạy migration, nên demo hôm nào số tiền phạt cũng giống kịch bản. Các bước bấm demo chi tiết có trong [../docs/10-chay-demo.md](../docs/10-chay-demo.md).

---

## Gặp lỗi thì sao

| Hiện tượng | Nguyên nhân / cách xử lý |
|---|---|
| `Java 17+ not found` | Chưa cài JDK, hoặc cài rồi nhưng chưa mở terminal mới. Làm lại bước 1.1 |
| `Missing src\main\resources\db.properties` | Chưa làm bước 3 |
| Web báo *Cannot connect to MySQL: Access denied* | Sai user / mật khẩu trong `db.properties` |
| Web báo *Communications link failure* | MySQL chưa chạy: mở `services.msc`, start `MySQL80` |
| Web báo *Unknown database 'libman06'* | Chưa chạy migration (bước 4) |
| `Address already in use` khi chạy | Cổng 8080 bị chiếm: `./run-demo.sh 8081` |
| Lần build đầu báo lỗi tải file | Máy chưa có Internet, hoặc mạng chặn `repo.maven.apache.org` |
| Chữ tiếng Việt bị lỗi khi chạy migration bằng dòng lệnh | Thiếu `--default-character-set=utf8mb4` trong lệnh `mysql` |
| Tiền phạt không ra 48.000 | Dữ liệu đã bị đổi sau lần demo trước: chạy lại migration |

---

## Cấu trúc thư mục

```
libman06-web/
├── database/libman06.sql          migration: tạo CSDL + dữ liệu demo (Câu 4a)
├── src/main/java/libman06/
│   ├── model/                     7 lớp thực thể (Reader06, ReaderCard06, ...)
│   ├── dao/                       DAO06 + 4 lớp DAO
│   ├── controller/                5 Servlet + AuthFilter06
│   └── LibManApp06.java           khởi động Tomcat nhúng
├── src/main/resources/
│   └── db.properties.example      mẫu cấu hình kết nối
├── src/main/webapp/
│   ├── WEB-INF/views/*.jsp        tầng View (các trang ...View06.jsp)
│   ├── WEB-INF/web.xml
│   └── assets/style.css
├── run-demo.sh                    build + chạy (Git Bash / macOS / Linux)
├── setup-db.sh                    chạy migration (đọc user/mật khẩu từ db.properties)
├── mvnw.cmd, mvnw, .mvn/          Maven Wrapper
└── pom.xml                        thư viện: Tomcat 10.1 (nhúng), JSTL 3.0, MySQL Connector/J 8.4
```
