package com.chengzzz.zcloud.service.fileservice.impl;


import com.chengzzz.zcloud.dto.BucketDTO;
import com.chengzzz.zcloud.dto.FileRequestDTO;
import com.chengzzz.zcloud.entity.FileEntity;
import com.chengzzz.zcloud.entity.FileEntityItem;
import com.chengzzz.zcloud.exception.PathErrorException;
import com.chengzzz.zcloud.service.core.AbstractBaseFileServerCore;
import com.chengzzz.zcloud.service.fileservice.IfileService;
import com.chengzzz.zcloud.utils.RedisCacheUtil;
import com.chengzzz.zcloud.utils.ZcloudFilesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.convert.Bucket;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * 文件服务类
 *
 * @author Yet
 * @date 2022/08/25 22:23
 **/
@Service
@Slf4j
public class FileServiceImpl extends AbstractBaseFileServerCore implements IfileService {


    @Resource
    ZcloudFilesUtil zcloudFilesUtil;

    @Resource
    RedisCacheUtil redisCacheUtil;

    @Override
    public boolean initFile2Cache(String bucketId) {

        return false;
    }

    @Override
    public List<FileEntityItem> getAllFiles(BucketDTO bucket) throws PathErrorException {
        return zcloudFilesUtil.getDirFromCurrentPath(bucket.getPath());
    }

    @Override
    public List<FileEntityItem> getDirFromCurrentPath(BucketDTO bucket) throws PathErrorException {
        return zcloudFilesUtil.getDirFromCurrentPath(bucket.getPath());
    }

    @Override
    public List<FileEntityItem> searchFiles(String name, String path) {
        return zcloudFilesUtil.searchFiles(name, path);
    }

    @Override
    public List<FileEntityItem> searchDirs(String name, String path) {
        return zcloudFilesUtil.searchDirs(name, path);
    }

    @Override
    public List<File> getDirectory(File file, List<File> result) {
        return zcloudFilesUtil.getDirectory(file, result);
    }

    @Override
    public File mkDir(String path) {
        return zcloudFilesUtil.mkDir(path);
    }

    @Override
    public boolean delDir(String path) {
        return zcloudFilesUtil.delDir(path);
    }

    @Override
    public void move(String source, String target, boolean isOverride) {
        zcloudFilesUtil.move(source, target, isOverride);
    }

    @Override
    public void copy(String source, String target, boolean isOverride) {
        zcloudFilesUtil.copy(source, target, isOverride);
    }

    @Override
    public List<FileEntityItem> getFileList(String path) {
        List<FileEntityItem> dirFromCurrentPath = null;
        try {
            dirFromCurrentPath = zcloudFilesUtil.getDirFromCurrentPath(path);
        }catch (Exception e){
            log.error("获取文件列表异常", e);
            return dirFromCurrentPath;
        }
        return dirFromCurrentPath;
    }

    @Override
    public String getUrl(String path) {
        return null;
    }
}
