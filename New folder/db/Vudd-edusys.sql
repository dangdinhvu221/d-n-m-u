CREATE DATABASE Vudd_EduSys;
GO
USE Vudd_EduSys;
GO

CREATE TABLE NhanVien
(
    MaNV NVARCHAR(50) PRIMARY KEY NOT NULL,
    MatKhau NVARCHAR(50) NOT NULL,
    HoTen NVARCHAR(255) NOT NULL,
    VaiTro BIT NOT NULL
);
GO

CREATE TABLE ChuyenDe
(
    MaCD NCHAR(5) PRIMARY KEY NOT NULL,
    TenCD NVARCHAR(50) NOT NULL,
    HocPhi MONEY NOT NULL,
    ThoiLuong INT NOT NULL,
    Hinh NVARCHAR(150) NOT NULL,
    MoTa NVARCHAR(255) NOT NULL,
);
GO

CREATE TABLE KhoaHoc
(
    MaKH INT IDENTITY(1, 1) PRIMARY KEY NOT NULL,
    MaCD NCHAR(5) NOT NULL,
    HocPhi MONEY NOT NULL,
    ThoiLuong INT NOT NULL,
    NgayKG DATE NOT NULL,
    GhiChu NVARCHAR(50) NOT NULL,
    MaNV NVARCHAR(50) NOT NULL,
    NgayTao DATE NOT NULL,
);
GO

CREATE TABLE NguoiHoc
(
    MaNH NCHAR(7) PRIMARY KEY NOT NULL,
    HoTen NVARCHAR(255) NOT NULL,
    NgaySinh DATE NOT NULL,
    GioiTinh BIT NOT NULL,
    DienThoai NVARCHAR(50) NOT NULL,
    Email NVARCHAR(50) NOT NULL,
    GhiChu NVARCHAR(MAX) NULL,
    MaNV NVARCHAR(50) NOT NULL,
    NgayDK DATE NOT NULL,
);
GO

CREATE TABLE HocVien
(
    MaHV INT IDENTITY(1, 1) PRIMARY KEY NOT NULL,
    MaKH INT NOT NULL,
    MaNH NCHAR(7) NOT NULL,
    Diem FLOAT NOT NULL,
);
GO

ALTER TABLE dbo.KhoaHoc
ADD
    FOREIGN KEY (MaCD) REFERENCES dbo.ChuyenDe (MaCD) ON DELETE NO ACTION ON UPDATE CASCADE;

ALTER TABLE dbo.KhoaHoc
ADD
    FOREIGN KEY (MaNV) REFERENCES dbo.NhanVien (MaNV) ON DELETE NO ACTION ON UPDATE CASCADE;

ALTER TABLE dbo.NguoiHoc
ADD
    FOREIGN KEY (MaNV) REFERENCES dbo.NhanVien (MaNV) ON DELETE NO ACTION ON UPDATE CASCADE;

ALTER TABLE dbo.HocVien
ADD
    FOREIGN KEY (MaNH) REFERENCES dbo.NguoiHoc (MaNH) ON DELETE NO ACTION ON UPDATE CASCADE;

ALTER TABLE dbo.HocVien
ADD
    FOREIGN KEY (MaKH) REFERENCES dbo.KhoaHoc (MaKH) ON DELETE CASCADE;


IF OBJECT_ID('sp_BangDiem') IS NOT NULL
    DROP PROC sp_BangDiem;
GO
CREATE PROC sp_BangDiem
(@MaKH INT)
AS
BEGIN
    SELECT NguoiHoc.MaNH,
           HoTen,
           Diem
    FROM dbo.HocVien
        JOIN dbo.NguoiHoc
            ON NguoiHoc.MaNH = HocVien.MaNH
    WHERE MaKH = @MaKH
    ORDER BY Diem DESC;
END;
GO

IF OBJECT_ID('sp_ThongKeDiem') IS NOT NULL
    DROP PROC sp_ThongKeDiem;
GO
CREATE PROC sp_ThongKeDiem
AS
BEGIN
    SELECT TenCD chuyenDe,
           COUNT(MaHV) soHV,
           MAX(Diem) caoNhat,
           MIN(Diem) thapNhat,
           AVG(Diem) trungBinh
    FROM dbo.KhoaHoc
        JOIN dbo.HocVien
            ON HocVien.MaKH = KhoaHoc.MaKH
        JOIN dbo.ChuyenDe
            ON ChuyenDe.MaCD = KhoaHoc.MaCD
    GROUP BY TenCD;
END;
GO



IF OBJECT_ID('sp_ThongKeDoanhThu') IS NOT NULL
    DROP PROC sp_ThongKeDoanhThu;
GO
CREATE PROC sp_ThongKeDoanhThu
(@Year INT)
AS
BEGIN
    SELECT TenCD chuyenDe,
           COUNT(DISTINCT KhoaHoc.MaKH) soKH,
           COUNT(MaHV) soHV,
           SUM(KhoaHoc.HocPhi) doanhThu,
           MIN(KhoaHoc.HocPhi) thapNhat,
           MAX(KhoaHoc.HocPhi) caoNhat,
           AVG(KhoaHoc.HocPhi) trungBinh
    FROM dbo.KhoaHoc
        JOIN dbo.HocVien
            ON HocVien.MaKH = KhoaHoc.MaKH
        JOIN dbo.ChuyenDe
            ON ChuyenDe.MaCD = KhoaHoc.MaCD
    WHERE YEAR(NgayKG) = @Year
    GROUP BY TenCD;
END;
EXEC dbo.sp_ThongKeDoanhThu @Year = 2021 -- int

GO

IF OBJECT_ID('sp_ThongKeNguoiHoc') IS NOT NULL
    DROP PROC sp_ThongKeNguoiHoc;
GO
CREATE PROC sp_ThongKeNguoiHoc
AS
BEGIN
    SELECT YEAR(NgayDK) Nam,
           COUNT(*) soLuong,
           MIN(NgayDK) DauTien,
           MAX(NgayDK) CuoiCung
    FROM dbo.NguoiHoc
    GROUP BY YEAR(NgayDK);
END;
DELETE FROM dbo.NhanVien
INSERT INTO dbo.NhanVien
(
    MaNV,
    MatKhau,
    HoTen,
    VaiTro
)
VALUES
(   N'Ducnv', -- MaNV - nvarchar(50)
    N'123', -- MatKhau - nvarchar(50)
    N'Nguyen Van Duc', -- HoTen - nvarchar(255)
    1 -- VaiTro - bit
    ),
	(   N'vudd', -- MaNV - nvarchar(50)
    N'123', -- MatKhau - nvarchar(50)
    N'Đặng Đình Vũ', -- HoTen - nvarchar(255)
    0 -- VaiTro - bit
    )

SELECT*FROM dbo.NhanVien