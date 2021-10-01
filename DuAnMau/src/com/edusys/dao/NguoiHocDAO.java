/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import com.edusys.entity.NguoiHoc;
import com.edusys.utils.Auth;
import com.edusys.utils.XDate;
import com.edusys.utils.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dangd
 */
public class NguoiHocDAO extends EduSysDAO<NguoiHoc, String> {

    String INSERT_SQL = "INSERT INTO NguoiHoc(MaNH, HoTen, NgaySinh, GioiTinh, DienThoai, Email, GhiChu, MaNV, NgayDK) VALUES (?,?,?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE NguoiHoc SET HoTen =?, NgaySinh =?, GioiTinh =?, DienThoai =?, Email =?, GhiChu =? WHERE MaNH = ?";
    String DELETE_SQL = "DELETE FROM dbo.NguoiHoc WHERE MaNH =?";
    String SELECT_ALL_SQL = "SELECT*FROM dbo.NguoiHoc";
    String SELECT_BY_ID = "SELECT*FROM dbo.NguoiHoc WHERE MaNH = ?";

    @Override
    public void insert(NguoiHoc entity) {
        jdbcHelper.update(INSERT_SQL,
                entity.getMaNH(), entity.getHoTen(), entity.getNgaySinh(), entity.isGioiTinh(), entity.getDienThoai(),
                entity.getEmail(), entity.getGhiChu(), Auth.user.getMaNV(), XDate.toString(new Date(), "yyyy/MM/dd"));
    }

    @Override
    public void update(NguoiHoc entity) {
        jdbcHelper.update(UPDATE_SQL,
                entity.getHoTen(), XDate.toString(entity.getNgaySinh(), "MM/dd/yyyy"), entity.isGioiTinh(), entity.getDienThoai(),
                entity.getEmail(), entity.getGhiChu(), entity.getMaNH());
    }

    @Override
    public void delete(String id) {
        jdbcHelper.update(DELETE_SQL, id);

    }

    @Override
    public NguoiHoc selectByID(String id) {
        List<NguoiHoc> list = this.selectBySql(SELECT_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<NguoiHoc> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);

    }

    @Override
    protected List<NguoiHoc> selectBySql(String sql, Object... args) {
        List<NguoiHoc> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                NguoiHoc entity = new NguoiHoc();
                entity.setMaNH(rs.getString("MaNH"));
                entity.setHoTen(rs.getString("HoTen"));
                entity.setNgaySinh(rs.getDate("NgaySinh"));
                entity.setGioiTinh(rs.getBoolean("GioiTinh"));
                entity.setDienThoai(rs.getString("DienThoai"));
                entity.setEmail(rs.getString("Email"));
                entity.setGhiChu(rs.getString("GhiChu"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setNgayDK(rs.getDate("NgayDK"));

                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<NguoiHoc> selectByKeyword(String key){
        String sql = "SELECT*FROM dbo.NguoiHoc WHERE HoTen LIKE ?";
        return selectBySql(sql, "%" + key + "%");
    }
    
    public List<NguoiHoc> selectNotInCourse(int makh, String keyWord){
        String sql = "SELECT * FROM NguoiHoc"
                + " WHERE HoTen LIKE ? AND"
                + " MaNH NOT IN (SELECT MaNH FROM HocVien WHERE MAKH = ?)";
        return this.selectBySql(sql, "%"+keyWord+"%", makh);
    }
}
