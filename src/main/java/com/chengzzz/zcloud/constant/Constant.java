package com.chengzzz.zcloud.constant;

import org.checkerframework.checker.index.qual.PolyUpperBound;

/**
 * zcloud静态常量类
 *
 * @author Yet
 * @date 2022/08/22 21:11
 **/
public class Constant {

    public static String PATH_ERROR = "路径错误";
    public static String BUCKET_ERROR = "该目录已作为存储空间，请更换存储源";

    public final static String OP_ADD = "新增";
    public final static String OP_ACCESS = "访问";
    public final static String OP_DEL = "删除";
    public final static String OP_MV = "移动";
    public final static String OP_CP = "复制";
    public final static String OP_DW = "下载";
    public final static String OP_PRE = "预览";


    public static String GLOBAL = "global:";
    public static String DEFAULT = "default:";
    public static String CONFIG = "config:";

    public static String FILE_CONFIG = "fileConfig:";

    public static String FILE_URL = "url:";
    public static String BUCKET = "bucket:";

    public static String FILES = "files:";
    public static String PATH_FILES = "paths:";

    public static String OTHER_ADMIN = "otherAdmins";
    public static String USER_NAME = "username";
    public static String USER_PASSWORD = "password";




    public static String GLOBAL_OTHER_ADMIN = GLOBAL+OTHER_ADMIN;
    public static String DEFAULT_USER_NAME = DEFAULT+USER_NAME;
    public static String DEFAULT_USER_PASSWORD = DEFAULT+USER_PASSWORD;



    public static String HTTP_OK = "请求成功!";
    public static String HTTP_BAD_REQUEST = "请求非法!";





}
