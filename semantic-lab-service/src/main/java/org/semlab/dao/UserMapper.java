package org.semlab.dao;

import com.vtech.mybatis.baseservice.base.BaseMapper;
import org.semlab.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User, Integer> {
}