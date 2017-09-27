package org.semlab.service.impl;

import com.vtech.mybatis.baseservice.base.BaseServiceImpl;
import org.semlab.model.User;
import org.semlab.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements UserService
{
}