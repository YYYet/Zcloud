package com.chengzzz.zcloud.service.cacheservice;

import com.chengzzz.zcloud.dto.BucketDTO;
import com.chengzzz.zcloud.dto.FileHandlerResult;
import com.chengzzz.zcloud.entity.FileEntity;
import com.chengzzz.zcloud.exception.PathErrorException;

import java.util.List;

public interface IcacheService {
    List<BucketDTO> getAllBucketFromCache();

    boolean checkBucketExistByPath(String path);

    BucketDTO getBucketByKey(String key);

    List<FileHandlerResult> initFile2Cache() throws PathErrorException;

    List<FileEntity> getAllFileFromCacheByPath(String path);
}
