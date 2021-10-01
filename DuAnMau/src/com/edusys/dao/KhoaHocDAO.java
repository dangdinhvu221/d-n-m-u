/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import com.edusys.entity.KhoaHoc;
import com.edusys.utils.jdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dangd
 */
public class KhoaHocDAO extends EduSysDAO<KhoaHoc, String> {

    String INSERT_SQL = "INSERT INTO KhoaHoc(MaCD, HocPhi, ThoiLuong, NgayKG, GhiChu, MaNV, NgayTao) VALUES(?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE dbo.KhoaHoc SET MaCD= ?, HocPhi = ?, ThoiLuong= ?,  NgayKG = ?, GhiChu = ?, MaNV = ?, NgayTao = ? WHERE MaKH = ?";
    String DELETE_SQL = "DELETE FROM dbo.KhoaHoc WHERE MaKH = ?";
    String SELECT_ALL_SQL = "SELECT * FROM dbo.KhoaHoc";
    String SELECT_BY_ID = "SELECT * FROM dbo.KhoaHoc WHERE MaKH = ?";

    @Override
    public void insert(KhoaHoc entity) {
        jdbcHelper.update(INSERT_SQL,
                entity.getMaCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getNgayKG(),
                entity.getGhiChu(), entity.getMaNV(), entity.getNgayTao());
    }

    @Override
    public void update(KhoaHoc entity) {
        jdbcHelper.update(UPDATE_SQL,
                entity.getMaCD(), entity.getHocPhi(), entity.getThoiLuong(), entity.getNgayKG(),
                entity.getGhiChu(), entity.getMaNV(), entity.getNgayTao(), entity.getMaKH());
    }

    @Override
    public void delete(String id) {
        jdbcHelper.update(DELETE_SQL, id);

    }

    @Override
    public KhoaHoc selectByID(String id) {
        List<KhoaHoc> list = this.selectBySql(SELECT_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<KhoaHoc> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<KhoaHoc> selectBySql(String sql, Object... args) {
        List<KhoaHoc> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                KhoaHoc entity = new KhoaHoc();
                entity.setMaKH(rs.getInt("MaKH"));
                entity.setMaCD(rs.getString("MaCD"));
                entity.setHocPhi(rs.getDouble("HocPhi"));
                entity.setThoiLuong(rs.getInt("ThoiLuong"));
                entity.setNgayKG(rs.getDate("NgayKG"));
                entity.setGhiChu(rs.getString("GhiChu"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setNgayTao(rs.getDate("NgayTao"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<KhoaHoc> selectByChuyenDe(String macd) {
        String sql = "SELECT * FROM KhoaHoc WHERE MaCD = ?";
        return this.selectBySql(sql, macd);
    }
    
    public List<Integer> selectYears(){
        String sql = "SELECT DISTINCT Year(NgayKG) FROM dbo.KhoaHoc ORDER BY Year(NgayKG) DESC";
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql);
            while (rs.next()) {                
                list.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
