package com.chengzzz.zcloud.controller;


import com.chengzzz.zcloud.constant.Constant;
import com.chengzzz.zcloud.dto.UserDTO;
import com.chengzzz.zcloud.responce.RestResponce;
import com.chengzzz.zcloud.utils.RedisCacheUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;


/**
 * 配置控制器
 *
 * @author Yet
 * @date 2022/08/25 20:20
 **/

@RestController
@RequestMapping("config")
public class ConfigController {

    @Resource
    RedisCacheUtil redisCacheUtil;

    @Resource
    ObjectMapper objectMapper;

    @GetMapping("otherAdmins")
    public RestResponce getAllOhterAdmins() throws JsonProcessingException {
        return RestResponce.sucess(redisCacheUtil.getCacheList(Constant.GLOBAL_OTHER_ADMIN));
    }

    @GetMapping("defalutAdmin")
    public RestResponce getDefalutAdmin(){
        UserDTO userDTO = new UserDTO(redisCacheUtil.getCacheObject(Constant.DEFAULT_USER_NAME), redisCacheUtil.getCacheObject(Constant.DEFAULT_USER_PASSWORD));
        return RestResponce.sucess(userDTO);
    }

}
