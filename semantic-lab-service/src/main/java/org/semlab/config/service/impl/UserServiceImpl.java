package org.semlab.config.service.impl;

import com.vtech.mybatis.baseservice.base.BaseServiceImpl;
import org.semlab.model.User;
import org.semlab.config.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements UserService
{
}