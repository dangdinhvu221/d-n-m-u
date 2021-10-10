/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import com.edusys.entity.NhanVien;
import com.edusys.utils.jdbcHelper;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author dangd
 */
public class NhanVienDAO extends EduSysDAO<NhanVien, String> {

    String INSERT_SQL = "INSERT INTO NhanVien (MaNV, HoTen, MatKhau, VaiTro) VALUES (?,?,?,?)";
    String UPDATE_SQL = "UPDATE dbo.NhanVien SET MatKhau = ?, HoTen = ?, VaiTro = ? WHERE MaNV = ?";
    String UPDATE_MK_SQL = "UPDATE dbo.NhanVien SET MatKhau = ? WHERE MaNV = ?";
    String DELETE_SQL = "DELETE FROM dbo.NhanVien WHERE MaNV=?";
    String SELECT_ALL_SQL = "SELECT * FROM dbo.NhanVien";
    String SELECT_BY_ID = "SELECT * FROM dbo.NhanVien WHERE [MaNV] = ?";
    
    public void updateResetPass(NhanVien entity){
        jdbcHelper.update(UPDATE_MK_SQL, entity.getMatKhau(), entity.getMaNV());
    }

    @Override
    public void insert(NhanVien entity) {
        jdbcHelper.update(INSERT_SQL,
                entity.getMaNV(), entity.getHoTen(), entity.getMatKhau(), entity.isVaiTro());
    }

    @Override
    public void update(NhanVien entity) {
        jdbcHelper.update(UPDATE_SQL,
                entity.getMatKhau(), entity.getHoTen(), entity.isVaiTro(), entity.getMaNV());
    }

    @Override
    public void delete(String id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public NhanVien selectByID(String id) {
        List<NhanVien> list = this.selectBySql(SELECT_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setHoTen(rs.getString("HoTen"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setVaiTro(rs.getBoolean("VaiTro"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
