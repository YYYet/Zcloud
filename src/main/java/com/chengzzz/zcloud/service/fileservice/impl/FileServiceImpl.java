package com.chengzzz.zcloud.service.fileservice.impl;


import com.chengzzz.zcloud.entity.FileEntity;
import com.chengzzz.zcloud.exception.PathErrorException;
import com.chengzzz.zcloud.service.fileservice.IfileService;
import com.chengzzz.zcloud.utils.ZcloudFilesUtil;
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
public class FileServiceImpl implements IfileService {


    @Resource
    ZcloudFilesUtil zcloudFilesUtil;

    @Override
    public List<FileEntity> getDirFromCurrentPath(String path) throws PathErrorException {
        return zcloudFilesUtil.getDirFromCurrentPath(path);
    }

    @Override
    public List<FileEntity> searchFiles(String name, String path) {
        return zcloudFilesUtil.searchFiles(name, path);
    }

    @Override
    public List<FileEntity> searchDirs(String name, String path) {
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
}
