package com.chengzzz.zcloud.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("User")
public class UserDo implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    Long id;
    String username;
    String password;
    String type;
    @TableLogic(value= "0", delval = "1")
    int deleted;
}