package com.chengzzz.zcloud.utils;

import cn.hutool.core.io.FileUtil;
import com.chengzzz.zcloud.entity.FileEntityItem;
import com.chengzzz.zcloud.exception.PathErrorException;
import com.chengzzz.zcloud.service.fileservice.impl.FileServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import static com.chengzzz.zcloud.constant.Constant.PATH_ERROR;

/**
 * zcloud文件工具类
 *
 * @author Yet
 * @date 2022/08/22 21:11
 **/


@Component
public class ZcloudFilesUtil {

    @Resource
    FileServiceImpl fileService;


    /**
     * 从当前路径中获取该层中所有的文件
     * @param path
     * @return
     * @throws PathErrorException
     */
    public  List<FileEntityItem> getDirFromCurrentPath(String path) throws PathErrorException {
        if (StringUtils.isEmpty(path)){
            throw new PathErrorException(PATH_ERROR);
        }
//        File[] ls = FileUtil.ls(path);
        List<File> files =  FileUtil.loopFiles(path);
        return FileFormatUtil.file2FileEntityItem(files);
    }

    /**
     * 模糊匹配文件
     * @param name
     * @param path
     * @return
     */
    public  List<FileEntityItem> searchFiles(String name, String path){
//        List<File> files = FileUtil.loopFiles(path);
        List<FileEntityItem> fileList = fileService.getFileList(path);
        List<FileEntityItem> collect = fileList.stream().filter(item -> item.getName().contains(name)).distinct().collect(Collectors.toList());
        return collect;
    }

    /**
     * 模糊匹配文件夹
     * @param name
     * @param path
     * @return
     */
    public  List<FileEntityItem> searchDirs(String name, String path){
        List<File> result = new ArrayList<>();
        List<File> directory = getDirectory(FileUtil.file(path), result);
        List<File> collect = directory.stream().filter(item -> item.getName().contains(name)).collect(Collectors.toList());
        return FileFormatUtil.file2FileEntityItem(collect);
    }

    /**
     * 递归获取文件夹
     * @param file
     * @param result
     * @return
     */
    public List<File> getDirectory(File file, List<File> result) {
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


    /**
     * 创建文件/文件夹
     * @param path
     * @return
     */
    public  File mkDir(String path){
      return FileUtil.mkdir(path);
    }


    /**
     * 删除文件/文件夹
     * @param path
     * @return
     */
    public  boolean delDir(String path){
        return FileUtil.del(path);
    }

    /**
     * 文件移动
     * @param source
     * @param target
     * @param isOverride
     */
    public  void move(String source, String target, boolean isOverride){
        FileUtil.move(new File(source), new File(target), isOverride);
    }

    /**
     * 文件拷贝
     * @param source
     * @param target
     * @param isOverride
     */
    public  void copy(String source, String target, boolean isOverride){
        FileUtil.copy(new File(source), new File(target), isOverride);
    }

    /**
     * 文件的大小转换
     * @param size
     * @return
     */
    public  String sizeFormat(long size){
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
