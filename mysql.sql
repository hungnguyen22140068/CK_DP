-- Tạo cơ sở dữ liệu với charset và collation hỗ trợ Unicode
CREATE DATABASE qlgdnhadat CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE qlgdnhadat;

-- Tạo bảng GiaoDichNha
CREATE TABLE GiaoDichNha (
    MaGiaoDich INT AUTO_INCREMENT PRIMARY KEY,
    LoaiNha VARCHAR(50) NOT NULL,
    DienTich INT NOT NULL,
    DonGia DECIMAL(18, 2) NOT NULL,
    TongTien DECIMAL(18, 2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Chèn dữ liệu vào bảng GiaoDichNha
INSERT INTO GiaoDichNha (LoaiNha, DienTich, DonGia, TongTien) VALUES
(N'Cao Cấp', 50, 5000000, 250000000),
(N'Thường', 50, 4000000, 180000000);

