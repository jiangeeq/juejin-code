package com.example.threadtransaction.dao;

import com.example.threadtransaction.JDBCUtils;
import com.example.threadtransaction.domain.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by jiangpeng on 2018/11/6.
 */
@Repository
public class TestUserDao {
    @Autowired
    DataSource dataSource;
    private Connection conn = JDBCUtils.getConnection();

    // 添加用户
    public void add(User user) throws SQLException {
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "insert into user(name) values(?)";
        Object[] params = {user.getName()};
        qr.update(sql, params);
    }

    // 删除用户
    public void delete(int id) throws SQLException {
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "delete from user where id=?";
        Object params = id;
        qr.update(sql, params);
    }

    // 修改用户
    public void update(User user) throws SQLException {
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "update user set name=?,age=? where id=?";
        Object[] params = {user.getName(), user.getId()};
        qr.update(sql, params);
    }

    // 查找某个用户
    public User find(int id) throws SQLException {
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "select * from user where id=?";
        Object params = id;
        User user = qr.query(sql, new BeanHandler<>(User.class), params);
        return user;
    }

    // 列出所有用户
    public List<User> getAllUser() throws SQLException {
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "select * from user";
        List<User> list = qr.query(sql, new BeanListHandler<>(User.class));
        return list;
    }
}