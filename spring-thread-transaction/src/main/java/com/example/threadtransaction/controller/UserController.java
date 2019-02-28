package com.example.threadtransaction.controller;

import com.example.threadtransaction.config.ThreadConfig;
import com.example.threadtransaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * Created by jiangpeng on 2018/11/6.
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/addUser1")
    public void addUser1() throws SQLException {
        userService.insert1();
    }

    @RequestMapping("/addUser2")
    public void addUser2() throws SQLException {
        userService.insert2();
    }

    @RequestMapping("/addUser3")
    public void addUser3() throws SQLException {
        userService.insert3();
    }

    @RequestMapping("/addUser4")
    public void addUser4() throws SQLException {
        userService.insert4();
    }

    @RequestMapping("/executeAsyncTask")
    public void executeAsyncTask() throws SQLException, InterruptedException {
        int i = 0;
        while (i < 100) {
            userService.executeAsyncTask();
            System.out.println(Thread.currentThread().getName() + "====>i = " + i++);
            Thread.sleep(50);
        }
    }
}
