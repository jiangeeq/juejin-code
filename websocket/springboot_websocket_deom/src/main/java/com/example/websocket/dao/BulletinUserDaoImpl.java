package com.example.websocket.dao;

import com.example.websocket.domain.BulletinUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jiangpeng on 2019/2/25.
 */
@Repository
public class BulletinUserDaoImpl implements BulletinUserDao {
    @Autowired
    private BulletinUserJPA bulletinUserJPA;

    @Override
    public void saveBulletinUser(BulletinUser bulletinUser) {
        bulletinUserJPA.save(bulletinUser);
    }

    @Override
    public Integer staffLastReadBulletinId(Integer staffId) {
        List<Integer> bulletinIds = bulletinUserJPA.staffLastReadBulletinIds(staffId);
        if (bulletinIds.size() == 0) {
            return 0;
        } else {
            return bulletinIds.get(0);
        }
    }
}
