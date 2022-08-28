package com.chengzzz.zcloud.service.core;

import com.chengzzz.zcloud.entity.FileEntity;
import com.chengzzz.zcloud.entity.FileEntityItem;

import java.util.List;

/**
 * @author Yet
 * @date 2022/08/28 13:02
 **/
public abstract class AbstractBaseFileServerCore implements FileServerCore {
    @Override
    public abstract List<FileEntityItem> getFileList(String path);

    @Override
    public abstract String getUrl(String path);
}
