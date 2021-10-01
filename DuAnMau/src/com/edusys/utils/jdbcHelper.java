/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author dangd
 */
public class jdbcHelper {
    // tham số để kết nối
    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName= Vudd_EduSys";
    static String username = "sa";
    static String password = "songlong";

    static {
        try {// nạp driver
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 4 phương thức jdbc
    // nhận vào tham số chuỗi sql và số lượng phần tử không giới hạn (String sql, Object... args).
    public static PreparedStatement getStmt(String sql, Object... args) throws Exception {
        // mở kết nối tới DB
        Connection conn = DriverManager.getConnection(connectionUrl, username, password);
        PreparedStatement pstmt;
        // nếu câu lệnh bắt đầu từ ký tự "{" thì là thủ tục lưu.
        // còn lại là câu lệnh truy vấn bth sql.
        
        if (sql.trim().startsWith("{")) {
            pstmt = conn.prepareCall(sql); // PROC
        } else {
            pstmt = conn.prepareStatement(sql); // sql
        }
        // duyệt số lượng tham số truyền vào từ (Object... args)
        // câu lệnh truy vấn có bn dấu "?" thì args có bấy nhiêu tham số 
        for (int i = 0; i < args.length; i++) {
            pstmt.setObject(i + 1, args[i]);
        }
        return pstmt;
    }

    public static int update(String sql, Object... args) {
        try {
            PreparedStatement pstmt = jdbcHelper.getStmt(sql, args);
            try {
                return pstmt.executeUpdate();
            } 
            finally {
                pstmt.getConnection().close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet query(String sql, Object... args) throws Exception {
        PreparedStatement pstmt = jdbcHelper.getStmt(sql, args);
        return pstmt.executeQuery();
    }

    public static Object value(String sql, Object... args) {
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            if (rs.next()) {
                return rs.getObject(0);
            }
            rs.getStatement().getConnection().close();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
