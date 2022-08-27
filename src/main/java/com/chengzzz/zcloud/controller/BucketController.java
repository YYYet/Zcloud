package com.chengzzz.zcloud.controller;

import com.chengzzz.zcloud.constant.Constant;
import com.chengzzz.zcloud.dto.BucketDTO;
import com.chengzzz.zcloud.responce.RestResponce;
import com.chengzzz.zcloud.service.cacheservice.impl.CacheServiceImpl;
import com.chengzzz.zcloud.utils.IdGenerateUtil;
import com.chengzzz.zcloud.utils.RedisCacheUtil;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yet
 * @date 2022/08/27 20:15
 **/
@RestController
@RequestMapping("/bucket")
public class BucketController {

    @Resource
    private RedisCacheUtil redisCacheUtil;

    @Resource
    private CacheServiceImpl cacheService;

    @GetMapping
    public RestResponce getAllBucketFromCache(){
       return RestResponce.sucess(cacheService.getAllBucketFromCache());
    }

    @PostMapping
    public RestResponce setBucketCache(@RequestBody BucketDTO bucketDTO){
        Assert.isTrue(cacheService.checkBucketExistByPath(bucketDTO.getPath()), Constant.BUCKET_ERROR);
        String id = IdGenerateUtil.IDWORK();
        bucketDTO.setBucketId(id);
        redisCacheUtil.setCacheObject(Constant.BUCKET+id, bucketDTO);
        return RestResponce.sucess();
    }

}
