package com.example.websocket.service;

import com.example.websocket.dao.BulletinUserDao;
import com.example.websocket.dao.BulletinUserJPA;
import com.example.websocket.domain.BulletinUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by jiangpeng on 2019/2/25.
 */
@Service
@Transactional
public class BulletinUserServiceImpl implements BulletinUserService {
    @Autowired
    private BulletinUserDao bulletinUserDao;
    @Autowired
    private BulletinUserJPA bulletinUserJPA;

    @Override
    public void save(BulletinUser bulletinUser) {
        bulletinUserDao.saveBulletinUser(bulletinUser);
    }

    /**
     * 确认读取通告后
     * 如果存在则更新状态为已读，否则新增已读记录
     *
     * @param bulletinId
     */
    @Override
    public void MarkReadBulletinUser(Integer bulletinId, Integer staffId) {
        BulletinUser bulletinUsers = bulletinUserJPA.findBulletinUserByBulletinIdAndAndUserId(bulletinId, staffId);
        if (bulletinUsers != null) {
            bulletinUserJPA.updateToRead(bulletinId, staffId);
        } else {
            BulletinUser bulletinUser = new BulletinUser();
            bulletinUser.setBulletinId(bulletinId);
            bulletinUser.setUserId(staffId);
            bulletinUser.setIsRead(1);
            bulletinUser.setCreatedAt(new Date());
            bulletinUser.setUpdatedAt(new Date());
            bulletinUserDao.saveBulletinUser(bulletinUser);
        }
    }
}
