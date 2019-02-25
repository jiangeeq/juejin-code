package com.example.websocket.dao;

import com.example.websocket.domain.BulletinUser;

import java.util.List;

/**
 * Created by jiangpeng on 2019/2/25.
 */
public interface BulletinUserDao {
    void saveBulletinUser(BulletinUser bulletinUser);

    /**
     * staffId读过的最后一条通告
     *
     * @param staffId
     * @return
     */
    Integer staffLastReadBulletinId(Integer staffId);

}