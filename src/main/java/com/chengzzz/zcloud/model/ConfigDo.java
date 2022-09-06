package com.chengzzz.zcloud.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Yet
 * @date 2022/09/01 22:36
 **/

@Data
@TableName("CommonConfig")
public class ConfigDo implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    Long id;
    String configName;
    String configValue;
}
