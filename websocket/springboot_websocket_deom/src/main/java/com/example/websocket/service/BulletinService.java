package com.example.websocket.service;

import com.example.websocket.domain.Bulletin;
import org.springframework.stereotype.Service;

/**
 * Created by jiangpeng on 2019/2/20.
 */
public interface BulletinService {
    Bulletin findBulletinById(Integer id);

    void saveBulletin(Bulletin bulletin);

    /**
     * 获取staffId没读的最新的通告
     *
     * @param staffId
     * @return
     */
    Bulletin getMyUnReadLastBulletin(Integer staffId);
}
