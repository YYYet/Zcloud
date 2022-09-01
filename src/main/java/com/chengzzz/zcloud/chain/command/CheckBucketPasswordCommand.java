package com.chengzzz.zcloud.chain.command;

import com.chengzzz.zcloud.chain.FileContext;
import com.chengzzz.zcloud.constant.Constant;
import com.chengzzz.zcloud.dto.FileConfigDTO;
import com.chengzzz.zcloud.utils.IpUtil;
import com.chengzzz.zcloud.utils.RedisCacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author Yet
 * @date 2022/08/27 23:58
 **/
@Service
@Slf4j
public class CheckBucketPasswordCommand implements Command {
    @Resource
    RedisCacheUtil redisCacheUtil;
    @Override
    public boolean execute(Context context) throws Exception {

        FileContext fileContext = (FileContext)context;
        if (!fileContext.getIsBucket()){
            String path = fileContext.getFileRequest().getPath();
            FileConfigDTO fileConfigDTO =  (FileConfigDTO)redisCacheUtil.getCacheObject(Constant.FILE_CONFIG+path);
            if (fileConfigDTO == null || ObjectUtils.isEmpty(fileConfigDTO.getPassword())){
                log.info("该文件无须密码校验");
                return false;
            }
            Assert.isTrue(fileConfigDTO.getPassword().contains(fileContext.getFileRequest().getPassword()), "您无权访问该文件，请联系管理员授权");
            return false;
        }
        log.info("开始校验bucket密码");
        if (StringUtils.isEmpty(fileContext.getBucketDTO().getPassword())){
            log.info("无需密码控制");
            return false;
        }
        Assert.isTrue(fileContext.getBucketDTO().getPassword().equals(fileContext.getFileRequest().getPassword()), "密码错误");
        log.info(fileContext.getBucketDTO().getPassword()+" bucket密码正确");
        return false;
    }
}
