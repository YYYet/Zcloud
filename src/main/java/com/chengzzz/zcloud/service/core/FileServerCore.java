package com.chengzzz.zcloud.service.core;

import com.chengzzz.zcloud.entity.FileEntity;
import com.chengzzz.zcloud.entity.FileEntityItem;

import java.util.List;

/**
 * @author Yet
 * @date 2022/08/28 13:01
 **/
public interface FileServerCore {

    List<FileEntityItem> getFileList(String path);

    String getUrl(String path);

}
