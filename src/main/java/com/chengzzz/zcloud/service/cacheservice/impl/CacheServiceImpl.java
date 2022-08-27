package com.chengzzz.zcloud.service.cacheservice.impl;

import com.chengzzz.zcloud.constant.Constant;
import com.chengzzz.zcloud.dto.BucketDTO;
import com.chengzzz.zcloud.service.cacheservice.IcacheService;
import com.chengzzz.zcloud.utils.RedisCacheUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yet
 * @date 2022/08/27 20:32
 **/
@Service
public class CacheServiceImpl implements IcacheService {

    @Resource
    private RedisCacheUtil redisCacheUtil;

    @Override
    public List<BucketDTO> getAllBucketFromCache() {
        Collection<String> keys = redisCacheUtil.keys(Constant.BUCKET.replace(":", "*"));
        return keys.stream().map(key -> (BucketDTO) redisCacheUtil.getCacheObject(key)).collect(Collectors.toList());
    }

    @Override
    public boolean checkBucketExistByPath(String path) {
        return getAllBucketFromCache().stream().filter(item->item.getPath().equals(path)).count() == 0;
    }

    @Override
    public BucketDTO getBucketByKey(String key) {
        return (BucketDTO)redisCacheUtil.getCacheObject(Constant.BUCKET+key);
    }
}
