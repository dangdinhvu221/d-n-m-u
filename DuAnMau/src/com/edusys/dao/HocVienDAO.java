/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import com.edusys.entity.HocVien;
import com.edusys.utils.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dangd
 */
public class HocVienDAO extends EduSysDAO<HocVien, Object> {

    String INSERT_SQL = "INSERT INTO HocVien ( MaKH, MaNH, Diem) VALUES (?,?,?)";
    String UPDATE_SQL = "UPDATE dbo.HocVien SET Diem = ? WHERE MaHV = ?";
    String DELETE_SQL = "DELETE FROM dbo.HocVien WHERE MaHV = ?";
    String SELECT_ALL_SQL = "SELECT*FROM dbo.HocVien";
    String SELECT_BY_ID = "SELECT*FROM dbo.HocVien WHERE MaHV = ?";

    @Override
    public void insert(HocVien entity) {
        jdbcHelper.update(INSERT_SQL,
                entity.getMaKH(), entity.getMaNH(), entity.getDiem());
    }

    @Override
    public void update(HocVien entity) {
        jdbcHelper.update(UPDATE_SQL,
                entity.getDiem(), entity.getMaHV());
    }

    @Override
    public void delete(Object id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public HocVien selectByID(Object id) {
        List<HocVien> list = this.selectBySql(SELECT_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<HocVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<HocVien> selectBySql(String sql, Object... args) {
        List<HocVien> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                HocVien entity = new HocVien();
                entity.setMaHV(rs.getInt("MaHV"));
                entity.setMaKH(rs.getInt("MaKH"));
                entity.setMaNH(rs.getString("MaNH"));
                entity.setDiem(rs.getDouble("Diem"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<HocVien> selectByKhoaHoc(int maKH){
        String sql = "SELECT * FROM dbo.HocVien WHERE MaKH = ?";
        return this.selectBySql(sql, maKH);
    }
}
