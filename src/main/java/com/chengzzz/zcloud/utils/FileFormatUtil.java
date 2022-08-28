package com.chengzzz.zcloud.utils;

import com.chengzzz.zcloud.entity.FileEntity;
import com.chengzzz.zcloud.entity.FileEntityItem;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件转换工具类
 *
 * @author Yet
 * @date 2022/08/25 22:30
 **/
public class FileFormatUtil {
    public static FileEntity file2FileEntity(File item){
        FileEntity fileEntity = new FileEntity(item.toURI());
        BeanUtils.copyProperties(item, fileEntity);
        fileEntity.setNeedHidden(false);
        /**
         * 极度影响性能
         */
//        FileEntity.setSize(sizeFormat(FileUtil.size(item)));
        return fileEntity;
    }

    public static FileEntityItem file2FileEntityItem(File item){
        FileEntityItem fileEntity = new FileEntityItem();
        BeanUtils.copyProperties(item, fileEntity);
        fileEntity.setNeedHidden(false);
        /**
         * 极度影响性能
         */
//        FileEntity.setSize(sizeFormat(FileUtil.size(item)));
        return fileEntity;
    }

    /**
     * 转换为文件封装类
     * @param item
     * @return
     */
    public static List<FileEntity> file2FileEntity(List<File> item){
        return  item.stream().map(file->file2FileEntity(file)).collect(Collectors.toList());
    }

    public static List<FileEntityItem> file2FileEntityItem(List<File> item){
        return  item.stream().map(file->file2FileEntityItem(file)).collect(Collectors.toList());
    }
}
