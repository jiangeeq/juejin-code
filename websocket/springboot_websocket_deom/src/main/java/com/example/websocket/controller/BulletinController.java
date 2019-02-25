package com.example.websocket.controller;

import com.example.websocket.domain.Bulletin;
import com.example.websocket.service.BulletinService;
import com.example.websocket.socket.BulletinWebSocket;
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
@RequestMapping("/bulletin")
public class BulletinController {
    @Autowired
    private BulletinService bulletinService;
    @Autowired
    private BulletinWebSocket bulletinWebSocket;

    /**
     * 根据id查找公告
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/findBulletin/{id}")
    public Bulletin findBulletin(@PathVariable("id") Integer id) {
        Bulletin bulletin = bulletinService.findBulletinById(id);
        return bulletin;
    }

    /**
     * 保存公告
     */
    @GetMapping(value = "/saveBulletin")
    public void saveBulletin() {
        Bulletin bulletin = new Bulletin();
        bulletin.setTitle("test title");
        bulletin.setContent("test content");
        bulletin.setStatus(0);
        bulletin.setUserType(3);
        bulletin.setCreatedAt(new Date());
        bulletin.setUpdatedAt(new Date());
        bulletin.setCreatedBy(1);
        bulletin.setUpdatedBy(1);
        bulletinService.saveBulletin(bulletin);
    }

    @GetMapping(value = "/sendMessage")
    public void sendMessage(){
        bulletinWebSocket.sendMessage(bulletinService.getMyUnReadLastBulletin(123));
    }
}
