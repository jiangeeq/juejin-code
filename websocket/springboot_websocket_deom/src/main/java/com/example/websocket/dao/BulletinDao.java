package com.example.websocket.dao;

import com.example.websocket.domain.Bulletin;

/**
 * Created by jiangpeng on 2019/2/25.
 */
public interface BulletinDao{
    /**
     * 根据id查找通告
     *
     * @param id
     * @return Bulletin
     */
    Bulletin findOne(Integer id);

    /**
     * 获取最新发布的通告
     * @return Bulletin
     */
    Bulletin findNewPublishBulletin();

    void save(Bulletin bulletin);
}
