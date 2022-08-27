package com.chengzzz.zcloud.service.userservice.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.chengzzz.zcloud.dao.UserMapper;
import com.chengzzz.zcloud.model.UserDo;
import com.chengzzz.zcloud.service.userservice.IUserService;
import org.springframework.stereotype.Service;

/**
 * @author Yet
 * @date 2022/08/28 00:43
 **/

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDo> implements IUserService{
}
