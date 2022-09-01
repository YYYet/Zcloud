package com.chengzzz.zcloud.service.cacheservice.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.chengzzz.zcloud.constant.Constant;
import com.chengzzz.zcloud.dto.BucketDTO;
import com.chengzzz.zcloud.dto.FileHandlerResult;
import com.chengzzz.zcloud.entity.FileEntity;
import com.chengzzz.zcloud.entity.FileEntityItem;
import com.chengzzz.zcloud.exception.PathErrorException;
import com.chengzzz.zcloud.service.cacheservice.IcacheService;
import com.chengzzz.zcloud.service.fileservice.impl.FileServiceImpl;
import com.chengzzz.zcloud.utils.RedisCacheUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Yet
 * @date 2022/08/27 20:32
 **/
@Service
public class CacheServiceImpl implements IcacheService {

    @Resource
    private RedisCacheUtil redisCacheUtil;

    @Resource
    private FileServiceImpl fileService;


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

    @Override
    public List<FileHandlerResult> initFile2Cache() throws PathErrorException {
        List<BucketDTO> allBucketFromCache = getAllBucketFromCache();
        List<FileHandlerResult> results = new ArrayList<>();
        TimeInterval timer = DateUtil.timer();
        for (BucketDTO bucketDTO : allBucketFromCache) {
            if (StringUtils.isEmpty(bucketDTO.getPath())){
                continue;
            }
            Map<String, FileEntityItem> dirFromCurrentPath = fileService.getDirFromCurrentPath(bucketDTO)
                    .stream().collect(Collectors.toMap(FileEntityItem::getPath, item -> item));

            redisCacheUtil.setCacheMap(Constant.FILES+bucketDTO.getBucketId(), dirFromCurrentPath);

//            List<FileEntityItem> dirFromCurrentPath = fileService.getDirFromCurrentPath(bucketDTO);
//            redisCacheUtil.setCacheList(Constant.FILES+bucketDTO.getBucketId(), dirFromCurrentPath);
            FileHandlerResult build = FileHandlerResult.builder()
                    .files(dirFromCurrentPath)
                    .bucket(bucketDTO)
                    .spendTime(String.valueOf(timer.intervalRestart() / 1000))
                    .completeTime(DateTime.now().toString())
                    .build();
            results.add(build);
        }
        return results;
    }

    @Override
    public List<FileEntity> getAllFileFromCacheByPath(String path) {
        return redisCacheUtil.getCacheList(Constant.FILES+path);
    }

    public Map getFileFromHashCacheByPath(String path) {
        return redisCacheUtil.getCacheMap(Constant.PATH_FILES+path);
    }
}
