package com.example.websocket.dao;

import com.example.websocket.domain.Bulletin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiangpeng on 2019/2/25.
 */
public interface BulletinJPA extends JpaRepository<Bulletin, Integer>, JpaSpecificationExecutor<Bulletin>, Serializable {
    @Query(value = "select * from bulletin where status = 1 ORDER BY publish_time DESC", nativeQuery = true)
    List<Bulletin> findNewPublishBulletin();
}
