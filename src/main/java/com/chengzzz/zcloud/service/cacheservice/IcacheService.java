package com.chengzzz.zcloud.service.cacheservice;

import com.chengzzz.zcloud.dto.BucketDTO;

import java.util.List;

public interface IcacheService {
    List<BucketDTO> getAllBucketFromCache();

    boolean checkBucketExistByPath(String path);

    BucketDTO getBucketByKey(String key);
}
