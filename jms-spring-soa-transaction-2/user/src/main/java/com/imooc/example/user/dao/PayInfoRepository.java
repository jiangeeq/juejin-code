package com.jls.example.user.dao;

import com.jls.example.user.domain.PayInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jiangpeng on 2018/10/10.
 */
public interface PayInfoRepository extends JpaRepository<PayInfo, Long> {
    PayInfo findOneByOrderId(Long orderId);
}
