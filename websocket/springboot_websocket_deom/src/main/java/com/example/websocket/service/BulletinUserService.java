package com.example.websocket.service;

import com.example.websocket.domain.BulletinUser;

/**
 * Created by jiangpeng on 2019/2/25.
 */
public interface BulletinUserService {
    void save(BulletinUser bulletinUser);
    /**
     * 确认读取通告后
     * 如果存在则更新状态为已读，否则新增已读记录
     *
     * @param bulletinId
     */
    void MarkReadBulletinUser(Integer bulletinId, Integer staffId);
}
