package com.chengzzz.zcloud.utils;

import cn.hutool.core.io.FileUtil;
import com.chengzzz.zcloud.entity.fileEntity;
import com.chengzzz.zcloud.entity.filesTree;
import com.chengzzz.zcloud.exception.pathErrorException;
import jdk.swing.interop.SwingInterOpUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import static com.chengzzz.zcloud.constant.constant.PATH_ERROR;

/**
 * zcloud文件工具类
 *
 * @author Yet
 * @date 2022/08/22 21:11
 **/


public class filesUtil {

    /**
     * 从当前路径中获取该层中所有的文件
     * @param path
     * @return
     * @throws pathErrorException
     */
    public static ArrayList<fileEntity> getDirFromCurrentPath(String path) throws pathErrorException {
        if (StringUtils.isEmpty(path)){
            throw new pathErrorException(PATH_ERROR);
        }

        File[] ls = FileUtil.ls(path);
        ArrayList<fileEntity> fileEntities = new ArrayList<>();

        for (File item : ls) {
            fileEntity fileEntity = new fileEntity(item.toURI());
            BeanUtils.copyProperties(item, fileEntity);
            fileEntity.setNeedHidden(false);
            fileEntity.setSize(sizeFormat(FileUtil.size(item)));
            fileEntities.add(fileEntity);
        }

        return fileEntities;
    }

    public static String sizeFormat(long size){
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

}
