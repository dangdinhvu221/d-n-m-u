/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import com.edusys.utils.jdbcHelper;
import java.text.DecimalFormat;

/**
 *
 * @author dangd
 */
public class ThongKeDAO {

    // getListOfArray dùng vào 4 hàm thủ tục lưu bên dưới
    // 3 tham số chuyền vào "sql", "cols", " Object...args"
    private List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        try {
            // tạo danh sách chứa các mã
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = jdbcHelper.query(sql, args); // dùng jdbcHelper.query(...)
            while (rs.next()) { // đọc qua từng rs
                Object[] vals = new Object[cols.length]; // 
                for (int i = 0; i < cols.length; i++) { // đọc các dòng qua mảng
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals); // add vào list
            }
            rs.getStatement().getConnection().close();// đóng
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 4 hàm gọi 4 thủ tục lưu
    public List<Object[]> getBangDiem(Integer makh) {
        String sql = "{CALL sp_BangDiem (?)}"; // câu lệnh gọi thủ tục
        String[] cols = {"MaNH", "HoTen", "Diem"}; // mảng các cột
        return this.getListOfArray(sql, cols, makh); // getListOfArray nhận vào sql, các cols, và đầu vào
    }

    public List<Object[]> getLuongNguoiHoc() {
        String sql = "{CALL sp_ThongKeNguoiHoc()}";
        String[] cols = {"Nam", "SoLuong", "DauTien", "CuoiCung"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getDiemChuyenDe() {
        String sql = "{CALL sp_ThongKeDiem()}";
        String[] cols = {"ChuyenDe", "SoHV", "ThapNhat", "CaoNhat", "TrungBinh"};
        return this.getListOfArray(sql, cols);
    }

    public List<Object[]> getDoanhThu(int nam) {
        String sql = "{CALL sp_ThongKeDoanhThu (?)}";
        String[] cols = {"ChuyenDe", "SoKH", "SoHV", "DoanhThu", "ThapNhat", "CaoNhat", "TrungBinh"};
        return this.getListOfArray(sql, cols, nam);
    }
}
