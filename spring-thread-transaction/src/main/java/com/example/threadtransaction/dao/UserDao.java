package com.example.threadtransaction.dao;

import com.example.threadtransaction.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by jiangpeng on 2018/11/6.
 */
@Repository
public class UserDao {
    @Autowired
    DataSource dataSource;

    // 添加用户
    public void add(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "insert into user(name) values(?)";
        jdbcTemplate.update(sql, user.getName());
    }
}
