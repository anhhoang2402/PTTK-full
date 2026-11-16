DROP DATABASE IF EXISTS libman06;
CREATE DATABASE libman06 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE libman06;

CREATE TABLE reader (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    full_name     VARCHAR(100) NOT NULL,
    date_of_birth DATE         NOT NULL,
    gender        VARCHAR(10)  NOT NULL,
    student_code  VARCHAR(20)  NOT NULL UNIQUE,
    class_name    VARCHAR(20)  NOT NULL,
    address       VARCHAR(255),
    email         VARCHAR(100) NOT NULL,
    phone         VARCHAR(15)  NOT NULL
);

CREATE TABLE reader_card (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    card_code   VARCHAR(20) UNIQUE,
    card_type   VARCHAR(20) NOT NULL,
    issue_date  DATE        NOT NULL,
    expiry_date DATE        NOT NULL,
    status      VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    reader_id   INT         NOT NULL UNIQUE,
    CONSTRAINT fk_card_reader FOREIGN KEY (reader_id) REFERENCES reader (id)
);

CREATE TABLE staff (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(50)  NOT NULL UNIQUE,
    password  CHAR(64)     NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    position  VARCHAR(50)  NOT NULL,
    phone     VARCHAR(15)
);

CREATE TABLE document (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    code         VARCHAR(20)   NOT NULL UNIQUE,
    title        VARCHAR(200)  NOT NULL,
    author       VARCHAR(100),
    publisher    VARCHAR(100),
    publish_year INT,
    category     VARCHAR(30)   NOT NULL,
    quantity     INT           NOT NULL DEFAULT 0,
    price        DECIMAL(12,2) NOT NULL DEFAULT 0
);

CREATE TABLE loan_slip (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    borrow_date    DATE NOT NULL,
    note           VARCHAR(255),
    reader_card_id INT  NOT NULL,
    staff_id       INT  NOT NULL,
    CONSTRAINT fk_slip_card  FOREIGN KEY (reader_card_id) REFERENCES reader_card (id),
    CONSTRAINT fk_slip_staff FOREIGN KEY (staff_id)       REFERENCES staff (id)
);

CREATE TABLE return_invoice (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    return_date    DATE          NOT NULL,
    total_fine     DECIMAL(12,2) NOT NULL DEFAULT 0,
    note           VARCHAR(255),
    reader_card_id INT           NOT NULL,
    staff_id       INT           NOT NULL,
    CONSTRAINT fk_invoice_card  FOREIGN KEY (reader_card_id) REFERENCES reader_card (id),
    CONSTRAINT fk_invoice_staff FOREIGN KEY (staff_id)       REFERENCES staff (id)
);

CREATE TABLE loan_detail (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    loan_slip_id      INT           NOT NULL,
    document_id       INT           NOT NULL,
    due_date          DATE          NOT NULL,
    return_date       DATE,
    late_fine         DECIMAL(12,2) NOT NULL DEFAULT 0,
    damage_fine       DECIMAL(12,2) NOT NULL DEFAULT 0,
    status            VARCHAR(20)   NOT NULL DEFAULT 'BORROWING',
    note              VARCHAR(255),
    return_invoice_id INT,
    CONSTRAINT fk_detail_slip     FOREIGN KEY (loan_slip_id)      REFERENCES loan_slip (id) ON DELETE CASCADE,
    CONSTRAINT fk_detail_document FOREIGN KEY (document_id)       REFERENCES document (id),
    CONSTRAINT fk_detail_invoice  FOREIGN KEY (return_invoice_id) REFERENCES return_invoice (id)
);

CREATE TABLE supplier (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(150) NOT NULL UNIQUE,
    address VARCHAR(255),
    email   VARCHAR(100),
    phone   VARCHAR(15),
    note    VARCHAR(255)
);

CREATE TABLE import_invoice (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    import_date  DATE          NOT NULL,
    total_amount DECIMAL(14,2) NOT NULL DEFAULT 0,
    note         VARCHAR(255),
    supplier_id  INT           NOT NULL,
    staff_id     INT           NOT NULL,
    CONSTRAINT fk_import_supplier FOREIGN KEY (supplier_id) REFERENCES supplier (id),
    CONSTRAINT fk_import_staff    FOREIGN KEY (staff_id)    REFERENCES staff (id)
);

CREATE TABLE import_detail (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    import_invoice_id INT           NOT NULL,
    document_id       INT           NOT NULL,
    quantity          INT           NOT NULL,
    unit_price        DECIMAL(12,2) NOT NULL,
    CONSTRAINT fk_import_detail_invoice  FOREIGN KEY (import_invoice_id) REFERENCES import_invoice (id) ON DELETE CASCADE,
    CONSTRAINT fk_import_detail_document FOREIGN KEY (document_id)       REFERENCES document (id)
);

INSERT INTO staff (username, password, full_name, position, phone) VALUES
('hoangva', SHA2('123456', 256), 'Trần Thị B', 'Library staff', '0987654321'),
('levanc',   SHA2('123456', 256), 'Lê Văn C',   'Library staff', '0987000111'),
('phamvane', SHA2('123456', 256), 'Phạm Văn E', 'Management staff', '0987000222');

INSERT INTO supplier (name, address, email, phone) VALUES
('Công ty Sách Alpha',         'Đống Đa, Hà Nội',  'contact@alphabooks.vn', '0243000111'),
('Nhà sách Giáo dục Hà Nội',   'Hoàn Kiếm, Hà Nội', 'sales@nsgd.vn',         '0243000222');

INSERT INTO document (code, title, author, publisher, publish_year, category, quantity, price) VALUES
('D001', 'Java Programming',              'Herbert Schildt',     'McGraw Hill',    2022, 'BOOK',     4, 250000),
('D002', 'Database Systems',              'Ramez Elmasri',       'Pearson',        2016, 'TEXTBOOK', 3, 320000),
('D003', 'UML Distilled',                 'Martin Fowler',       'Addison-Wesley', 2003, 'BOOK',     2, 180000),
('D004', 'Phân tích thiết kế hệ thống',   'Nguyễn Văn X',        'NXB Giáo dục',   2020, 'TEXTBOOK', 5, 90000),
('D005', 'Clean Code',                    'Robert C. Martin',    'Prentice Hall',  2008, 'BOOK',     3, 210000),
('D006', 'Tạp chí Khoa học Máy tính số 3','Hội Tin học VN',      'Hội Tin học VN', 2026, 'JOURNAL',  6, 40000),
('D007', 'Design Patterns',               'Erich Gamma et al.',  'Addison-Wesley', 1994, 'BOOK',     2, 280000);

INSERT INTO reader (full_name, date_of_birth, gender, student_code, class_name, address, email, phone) VALUES
('Phạm Minh Đức',  '2004-02-11', 'Male',   'B21DCCN001', 'D21CNPM01', 'Thanh Xuân, Hà Nội', 'duc.pm@stu.edu.vn',  '0911000001'),
('Hoàng Thu Hà',   '2004-07-23', 'Female', 'B21DCCN002', 'D21CNPM02', 'Cầu Giấy, Hà Nội',   'ha.ht@stu.edu.vn',   '0911000002'),
('Vũ Quang Huy',   '2003-12-01', 'Male',   'B20DCCN003', 'D20CNPM03', 'Hà Đông, Hà Nội',    'huy.vq@stu.edu.vn',  '0911000003'),
('Đỗ Thị Lan',     '2005-03-15', 'Female', 'B22DCCN004', 'D22CNPM04', 'Đống Đa, Hà Nội',    'lan.dt@stu.edu.vn',  '0911000004'),
('Ngô Văn Nam',    '2004-09-30', 'Male',   'B21DCCN005', 'D21CNPM05', 'Long Biên, Hà Nội',  'nam.nv@stu.edu.vn',  '0911000005'),
('Nguyễn Văn A',   '2004-05-16', 'Male',   'B21DCCN006', 'D21CNPM06', 'Hà Đông, Hà Nội',    'a.nv@stu.edu.vn',    '0912345606');

INSERT INTO reader_card (card_code, card_type, issue_date, expiry_date, status, reader_id) VALUES
('RC000001', 'Student', '2025-09-05', '2029-09-05', 'ACTIVE', 1),
('RC000002', 'Student', '2025-09-05', '2029-09-05', 'ACTIVE', 2),
('RC000003', 'Student', '2024-09-10', '2028-09-10', 'ACTIVE', 3),
('RC000004', 'Student', '2026-09-08', '2030-09-08', 'ACTIVE', 4),
('RC000005', 'Student', '2025-09-05', '2029-09-05', 'ACTIVE', 5),
('RC000006', 'Student', '2025-09-12', '2029-09-12', 'ACTIVE', 6);

INSERT INTO loan_slip (id, borrow_date, note, reader_card_id, staff_id) VALUES
(1, CURDATE() - INTERVAL 28 DAY, NULL, 6, 1),
(2, CURDATE() - INTERVAL 19 DAY, NULL, 6, 2),
(3, CURDATE() - INTERVAL 9 DAY,  NULL, 6, 1),
(4, CURDATE() - INTERVAL 40 DAY, NULL, 1, 1),
(5, CURDATE() - INTERVAL 3 DAY,  NULL, 3, 2);

INSERT INTO loan_detail (loan_slip_id, document_id, due_date, status) VALUES
(1, 1, CURDATE() - INTERVAL 14 DAY, 'BORROWING'),
(2, 2, CURDATE() - INTERVAL 5 DAY,  'BORROWING'),
(3, 3, CURDATE() + INTERVAL 5 DAY,  'BORROWING'),
(5, 5, CURDATE() + INTERVAL 11 DAY, 'BORROWING');

INSERT INTO return_invoice (id, return_date, total_fine, note, reader_card_id, staff_id) VALUES
(1, CURDATE() - INTERVAL 25 DAY, 0, NULL, 1, 1);
INSERT INTO loan_detail (loan_slip_id, document_id, due_date, return_date, late_fine, damage_fine, status, return_invoice_id) VALUES
(4, 4, CURDATE() - INTERVAL 25 DAY, CURDATE() - INTERVAL 25 DAY, 0, 0, 'RETURNED', 1);

INSERT INTO import_invoice (id, import_date, total_amount, note, supplier_id, staff_id) VALUES
(1, '2026-09-01', 1120000, NULL, 1, 2),
(2, '2026-09-15', 450000,  NULL, 2, 2);
INSERT INTO import_detail (import_invoice_id, document_id, quantity, unit_price) VALUES
(1, 1, 2, 250000),
(1, 3, 2, 180000),
(1, 5, 1, 260000),
(2, 4, 5, 90000);
