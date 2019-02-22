package com.example.websocket.service;

import org.springframework.stereotype.Service;

/**
 * Created by jiangpeng on 2019/2/20.
 */
@Service(value = "BulletinService")
public class BulletinService {

    public String getBulletin() {
        return "你是可以看到我的！";
    }
}
