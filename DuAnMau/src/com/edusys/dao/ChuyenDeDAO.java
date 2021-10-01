/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import com.edusys.entity.ChuyenDe;
import com.edusys.utils.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dangd
 */
public class ChuyenDeDAO extends EduSysDAO<ChuyenDe, String> {

    String INSERT_SQL = "INSERT INTO ChuyenDe (MaCD, TenCD, HocPhi, ThoiLuong, Hinh, MoTa) VALUES (?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE ChuyenDe SET TenCD = ?, HocPhi =?, ThoiLuong = ?, Hinh = ?, MoTa = ? WHERE MaCD =?";
    String DELETE_SQL = "DELETE FROM dbo.ChuyenDe WHERE MaCD = ?";
    String SELECT_ALL_SQL = "SELECT * FROM dbo.ChuyenDe";
    String SELECT_BY_ID = "SELECT * FROM dbo.ChuyenDe WHERE MaCD = ?";

    @Override
    public void insert(ChuyenDe entity) {
        jdbcHelper.update(INSERT_SQL,
                entity.getMaCD(), entity.getTenCD(), entity.getHocPhi(), entity.getThoiLuong(),
                entity.getHinh(), entity.getMoTa());
    }

    @Override
    public void update(ChuyenDe entity) {
        jdbcHelper.update(UPDATE_SQL,
                entity.getTenCD(), entity.getHocPhi(), entity.getThoiLuong(),
                entity.getHinh(), entity.getMoTa(), entity.getMaCD());
    }

    @Override
    public void delete(String id) {
        jdbcHelper.update(DELETE_SQL, id);

    }

    @Override
    public ChuyenDe selectByID(String id) {
        List<ChuyenDe> list = this.selectBySql(SELECT_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<ChuyenDe> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<ChuyenDe> selectBySql(String sql, Object... args) {
        List<ChuyenDe> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                ChuyenDe entity = new ChuyenDe();
                entity.setMaCD(rs.getString("MaCD"));
                entity.setTenCD(rs.getString("TenCD"));
                entity.setHocPhi(rs.getDouble("HocPhi"));
                entity.setThoiLuong(rs.getInt("ThoiLuong"));
                entity.setHinh(rs.getString("Hinh"));
                entity.setMoTa(rs.getString("MoTa"));

                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
