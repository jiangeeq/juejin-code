package com.example.websocket.service;

import com.example.websocket.dao.BulletinDao;
import com.example.websocket.dao.BulletinUserDao;
import com.example.websocket.domain.Bulletin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jiangpeng on 2019/2/25.
 */
@Service
@Transactional
public class BulletinServiceImpl implements BulletinService {
    @Autowired
    private BulletinDao bulletinDao;
    @Autowired
    private BulletinUserDao bulletinUserDao;

    @Override
    public Bulletin findBulletinById(Integer id) {
        return bulletinDao.findOne(id);
    }

    @Override
    public void saveBulletin(Bulletin bulletin) {
        bulletinDao.save(bulletin);
    }

    /**
     * 获取staffId没读的最新的通告
     *
     * @param staffId
     * @return
     */
    @Override
    public Bulletin getMyUnReadLastBulletin(Integer staffId) {
        // 获取最新发布的通告id
        Bulletin newPublishBulletin = bulletinDao.findNewPublishBulletin();
        if (newPublishBulletin != null) {
            // 我读过的最后一条通告
            Integer readBulletinId = bulletinUserDao.staffLastReadBulletinId(staffId);
            if (readBulletinId == 0 || readBulletinId.equals(newPublishBulletin.getId())) {
                return newPublishBulletin;
            }
        }
        return null;
    }
}
