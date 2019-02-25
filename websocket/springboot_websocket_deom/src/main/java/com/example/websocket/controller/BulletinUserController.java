package com.example.websocket.controller;

import com.example.websocket.domain.BulletinUser;
import com.example.websocket.service.BulletinUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by jiangpeng on 2019/2/25.
 */
@RestController
@RequestMapping("/bulletinUser")
public class BulletinUserController {
    @Autowired
    private BulletinUserService bulletinUserService;

    /**
     * 保存阅读记录
     */
    @GetMapping("/save")
    public void save() {
        BulletinUser bulletinUser = new BulletinUser();
        bulletinUser.setBulletinId(1);
        // 测试写死staffId
        bulletinUser.setUserId(123);
        bulletinUser.setIsRead(1);
        bulletinUser.setCreatedAt(new Date());
        bulletinUser.setUpdatedAt(new Date());
        bulletinUserService.save(bulletinUser);
    }

    @GetMapping("/read/{bulletinId}")
    public void read(@PathVariable("bulletinId") Integer bulletinId) {
        // 测试写死staffId
        bulletinUserService.MarkReadBulletinUser(bulletinId, 123);
    }


}
