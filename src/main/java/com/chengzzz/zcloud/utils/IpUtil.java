package com.chengzzz.zcloud.utils;

import com.chengzzz.zcloud.constant.Constant;
import com.chengzzz.zcloud.dto.BucketDTO;
import com.chengzzz.zcloud.dto.FileRequestDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Yet
 * @date 2022/08/27 23:21
 **/

public class IpUtil {

    @Resource
    static RedisCacheUtil redisCacheUtil;

    public static String appendIp(BucketDTO bucket, FileRequestDTO fileRequest){
        String limitIpGroup = bucket.getLimitIp();
        limitIpGroup += ","+fileRequest.getUserIp();
        bucket.setLimitIp(limitIpGroup);
        redisCacheUtil.setCacheObject(Constant.BUCKET+bucket.getBucketId(), bucket);
        return limitIpGroup;
    }

    public static boolean hasLimitIp(BucketDTO bucket, FileRequestDTO fileRequest){
        return !bucket.getLimitIp().contains(fileRequest.getUserIp());
    }

    public static boolean removeLimitIp(BucketDTO bucket, FileRequestDTO fileRequest){
        bucket.setLimitIp(bucket.getLimitIp().replace(","+fileRequest.getUserIp(), ""));
        redisCacheUtil.setCacheObject(Constant.BUCKET+bucket.getBucketId(), bucket);
        return true;
    }

}
