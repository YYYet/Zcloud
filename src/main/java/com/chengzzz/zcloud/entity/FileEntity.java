package com.chengzzz.zcloud.entity;


import java.io.File;
import java.net.URI;

/**
 * 文件实体类
 *
 * @author Yet
 * @date 2022/08/22 21:27
 **/
public class FileEntity extends File {

    private Integer Order;
    private Boolean NeedHidden;

    private String size;

    public FileEntity(String pathname) {
        super(pathname);
    }

    public FileEntity(String parent, String child) {
        super(parent, child);
    }

    public FileEntity(File parent, String child) {
        super(parent, child);
    }

    public FileEntity(URI uri) {
        super(uri);
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isNeedHidden(){
        return this.NeedHidden;
    }

    public Integer getOrder() {
        return Order;
    }

    public void setOrder(Integer order) {
        Order = order;
    }

    public void setNeedHidden(Boolean needHidden) {
        NeedHidden = needHidden;
    }
}
