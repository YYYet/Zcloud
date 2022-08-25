package com.chengzzz.zcloud.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;

import java.util.UUID;

/**
 * 唯一id生成工具类
 *
 * @author Yet
 * @date 2022/08/25 22:39
 **/
public class IdGenerateUtil {

    static String UUID(){
        return IdUtil.simpleUUID();
    }

    static String IDWORK(){
        return IdUtil.getSnowflake().nextIdStr();
    }
}
