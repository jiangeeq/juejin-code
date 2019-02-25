package com.example.websocket.dao;

import com.example.websocket.domain.BulletinUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiangpeng on 2019/2/25.
 */
public interface BulletinUserJPA extends JpaRepository<BulletinUser, Integer>, JpaSpecificationExecutor<BulletinUser>, Serializable {
    @Query(value = "SELECT bulletin_id from bulletin_user WHERE user_id = :staffId and is_read =1 ORDER BY created_at DESC", nativeQuery = true)
    List<Integer> staffLastReadBulletinIds(@Param("staffId") Integer staffId);

    BulletinUser findBulletinUserByBulletinIdAndAndUserId(Integer bulletinId, Integer userId);

    @Query(value = "update bulletin_user set is_read = 1 where bulletin_id = ?1 and user_id = ?2", nativeQuery = true)
    @Modifying
    void updateToRead(Integer bulletinId, Integer staffId);
}
