package com.example.websocket.dao;

import com.example.websocket.domain.Bulletin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jiangpeng on 2019/2/25.
 */
@Repository
public class BulletinDaoImpl implements BulletinDao {
    @Autowired
    private BulletinJPA bulletinJPA;

    @Override
    public Bulletin findOne(Integer id) {
        return bulletinJPA.getOne(id);
    }

    @Override
    public Bulletin findNewPublishBulletin() {
        List<Bulletin> bulletins = bulletinJPA.findNewPublishBulletin();
        if (bulletins.size() == 0) {
            return null;
        } else {
            return bulletins.get(0);
        }
    }

    @Override
    public void save(Bulletin bulletin) {
        bulletinJPA.save(bulletin);
    }
}
