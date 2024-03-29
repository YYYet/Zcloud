package com.chengzzz.zcloud.controller;

import com.chengzzz.zcloud.model.UserDo;
import com.chengzzz.zcloud.responce.RestResponce;
import com.chengzzz.zcloud.service.userservice.IUserService;
import com.chengzzz.zcloud.service.userservice.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Yet
 * @date 2022/08/28 01:02
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserServiceImpl userService;

    @GetMapping
    public RestResponce query(){
        return RestResponce.sucess(userService.list());
    }

    @PostMapping
    public RestResponce insert(){
        UserDo userDo = new UserDo();
        userDo.setPassword("123");
        userDo.setUsername("123");
        userDo.setType("23");
        userService.save(userDo);
        return RestResponce.sucess(userService.list());
    }
}
