package com.chengzzz.zcloud.service.fileservice;

import cn.hutool.core.io.FileUtil;
import com.chengzzz.zcloud.entity.FileEntity;
import com.chengzzz.zcloud.exception.PathErrorException;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.List;


public interface IfileService {
    /**
     * 从当前路径中获取该层中所有的文件
     * @param path
     * @return
     * @throws PathErrorException
     */
    List<FileEntity> getDirFromCurrentPath(String path) throws PathErrorException;

    /**
     * 模糊匹配文件
     * @param name
     * @param path
     * @return
     */
    List<FileEntity> searchFiles(String name, String path);

    /**
     * 模糊匹配文件夹
     * @param name
     * @param path
     * @return
     */
    List<FileEntity> searchDirs(String name, String path);

    /**
     * 递归获取文件夹
     * @param file
     * @param result
     * @return
     */
    List<File> getDirectory(File file, List<File> result);

    /**
     * 创建文件/文件夹
     * @param path
     * @return
     */
    File mkDir(String path);


    /**
     * 删除文件/文件夹
     * @param path
     * @return
     */
    boolean delDir(String path);

    /**
     * 文件移动
     * @param source
     * @param target
     * @param isOverride
     */
     void move(String source, String target, boolean isOverride);

    /**
     * 文件拷贝
     * @param source
     * @param target
     * @param isOverride
     */
     void copy(String source, String target, boolean isOverride);
}
