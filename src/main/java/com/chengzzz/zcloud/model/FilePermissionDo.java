package com.chengzzz.zcloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Yet
 * @date 2022/08/28 00:37
 **/

@Data
@TableName("FilePermission")
public class FilePermissionDo implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    Long id;
    String bucketId;
    String path;
    String password;
    String url;
    String limitIp;
}
