package com.chengzzz.zcloud.utils;

import cn.hutool.core.io.FileUtil;
import com.chengzzz.zcloud.entity.fileEntity;
import com.chengzzz.zcloud.exception.pathErrorException;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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
            fileEntities.add(file2FileEntity(item));
        }
        return fileEntities;
    }

    /**
     * 模糊匹配文件
     * @param name
     * @param path
     * @return
     */
    public static List<fileEntity> searchFiles(String name, String path){
        List<File> files = FileUtil.loopFiles(path);
        List<File> collect = files.stream().filter(item -> item.getName().contains(name)).distinct().collect(Collectors.toList());
        return file2FileEntity(collect);
    }

    /**
     * 模糊匹配文件夹
     * @param name
     * @param path
     * @return
     */
    public static List<fileEntity> searchDirs(String name, String path){
        List<File> result = new ArrayList<>();
        List<File> directory = getDirectory(FileUtil.file(path), result);
        List<File> collect = directory.stream().filter(item -> item.getName().contains(name)).collect(Collectors.toList());
        return file2FileEntity(collect);
    }

    /**
     * 递归获取文件夹
     * @param file
     * @param result
     * @return
     */
    private static List<File> getDirectory(File file, List<File> result) {
        File flist[] = file.listFiles();
        if (flist == null || flist.length == 0) {
            return result;
        }
        for (File f : flist) {
            if (f.isDirectory()) {
                //这里将列出所有的文件夹
                result.add(f);
                getDirectory(f, result);
            }
        }
        return result;
    }

    /**
     * 转换为文件封装类
     * @param item
     * @return
     */
    public static fileEntity file2FileEntity(File item){
        fileEntity fileEntity = new fileEntity(item.toURI());
        BeanUtils.copyProperties(item, fileEntity);
        fileEntity.setNeedHidden(false);
        fileEntity.setSize(sizeFormat(FileUtil.size(item)));
        return fileEntity;
    }

    /**
     * 转换为文件封装类
     * @param item
     * @return
     */
    public static List<fileEntity> file2FileEntity(List<File> item){
        List result = new ArrayList();
        item.forEach(file ->  result.add(file2FileEntity(file)));
        return result;
    }

    /**
     * 创建文件/文件夹
     * @param path
     * @return
     */
    public static File mkDir(String path){
      return FileUtil.mkdir(path);
    }


    /**
     * 删除文件/文件夹
     * @param path
     * @return
     */
    public static boolean delDir(String path){
        return FileUtil.del(path);
    }

    /**
     * 文件移动
     * @param source
     * @param target
     * @param isOverride
     */
    public static void move(String source, String target, boolean isOverride){
        FileUtil.move(new File(source), new File(target), isOverride);
    }

    /**
     * 文件拷贝
     * @param source
     * @param target
     * @param isOverride
     */
    public static void copy(String source, String target, boolean isOverride){
        FileUtil.copy(new File(source), new File(target), isOverride);
    }

    /**
     * 文件的大小转换
     * @param size
     * @return
     */
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
