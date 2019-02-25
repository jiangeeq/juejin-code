package com.example.websocket.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by jiangpeng on 2019/2/25.
 * @Id @Id  使用联合主键时，bean类必须序列化
 * @IdClass  使用联合主键时
 */
@Entity
@Table(name = "bulletin_user")
@IdClass(BulletinUser.class)
public class BulletinUser implements Serializable {
    @Id
    @Column(name = "bulletin_id")
    private Integer bulletinId;
    @Id
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "is_read")
    private Integer isRead;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;

    @Override
    public String toString() {
        return "BulletinUser{" +
                "bulletinId=" + bulletinId +
                ", userId=" + userId +
                ", isRead=" + isRead +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public Integer getBulletinId() {
        return bulletinId;
    }

    public void setBulletinId(Integer bulletinId) {
        this.bulletinId = bulletinId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
