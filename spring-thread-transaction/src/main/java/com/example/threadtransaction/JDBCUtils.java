package com.example.threadtransaction;

/**
 * Created by jiangpeng on 2018/11/6.
 */

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class JDBCUtils {
    private static Properties properties;
    private static String url;
    private static String driverClass;
    private static String username;
    private static String password;
    private static Connection connection = null;
    private static PreparedStatement pstmt;

    static {
        properties = new Properties();
        try {
            // properties.load(new FileInputStream(new File("db.properties")));
            properties.load(JDBCUtils.class.getClassLoader().getResourceAsStream("db.properties"));
            url = properties.getProperty("url");
            driverClass = properties.getProperty("driverClass");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //得到连接
    public static Connection getConnection() {
        try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //关闭所有连接
    public static void closeAll(Connection conn, Statement statement, ResultSet resultSet) {

        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (conn != null) {
                conn.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //增加，删除，修改
    public static boolean updateByPreparedStatement(String sql, List<Object> params) throws Exception {
        boolean flag = false;
        int result = -1;
        pstmt = connection.prepareStatement(sql);
        int index = 1;
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        result = pstmt.executeUpdate();
        flag = result > 0 ? true : false;
        return flag;
    }

    //查询单条记录
    public static Map<String, Object> findSimpleResult(String sql, List<Object> params) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        int index = 1;
        pstmt = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        ResultSet resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int col_len = metaData.getColumnCount();
        while (resultSet.next()) {
            for (int i = 0; i < col_len; i++) {
                String columnName = metaData.getColumnName(i + 1);
                Object res_value = resultSet.getObject(columnName);
                if (res_value == null) {
                    res_value = "";
                }
                map.put(columnName, res_value);
            }
        }
        return map;
    }

    //多条记录查询
    public List<Map<String, Object>> findModeResult(String sql, List<Object> params) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int index = 1;
        pstmt = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        ResultSet resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while (resultSet.next()) {
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < cols_len; i++) {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = resultSet.getObject(cols_name);
                if (cols_value == null) {
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
            list.add(map);
        }
        return list;
    }
}