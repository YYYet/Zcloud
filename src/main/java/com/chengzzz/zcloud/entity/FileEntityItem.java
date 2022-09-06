package com.chengzzz.zcloud.entity;


import lombok.Data;
import lombok.Getter;

import java.io.File;
import java.net.URI;
import java.util.List;

/**
 * 文件实体类
 *
 * @author Yet
 * @date 2022/08/22 21:27
 **/
@Data
@Getter
public class FileEntityItem {

    private String name;
    private String desc;
    private String path;
    private String url;
    private Integer Order;
    private long downLoadCount;
    private Boolean NeedHidden;
    private String size;
    private String password = "";
    private String lastModifyDate;
    private String suffix;
    private List<String> whiteIpList;
    private boolean isDirectory;
    private boolean exists;
    private boolean isFile;
    private boolean isHidden;
    private boolean lastModified;
    private long length;

}
