package com.jpsite.interesting.repository;

import com.jpsite.interesting.domain.ShortLink;
import org.springframework.data.repository.CrudRepository;

/**
 * CrudRepository 实现了对 ShortLink 基本的增删改查方法
 * @author jiangpeng
 * @date 2019/11/2715:29
 */
public interface ShortLinkRepository extends CrudRepository<ShortLink, Long> {

}
