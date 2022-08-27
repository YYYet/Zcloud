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

    private String password;
    private String limitIp;
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

    public String getLimitIp() {
        return limitIp;
    }

    public void setLimitIp(String limitIp) {
        this.limitIp = limitIp;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNeedHidden(Boolean needHidden) {
        NeedHidden = needHidden;
    }
}
