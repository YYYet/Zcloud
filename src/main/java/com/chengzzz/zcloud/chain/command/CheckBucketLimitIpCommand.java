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
 * @date 2022/08/27 23:19
 **/
@Service
@Slf4j
public class CheckBucketLimitIpCommand implements Command {

    @Resource
    RedisCacheUtil redisCacheUtil;
    @Override
    public boolean execute(Context context) throws Exception {
        FileContext fileContext = (FileContext)context;
        if (!fileContext.getIsBucket()){
            String path = fileContext.getFileRequest().getPath().replace("\\", "\\\\");
            FileConfigDTO fileConfigDTO =  (FileConfigDTO)redisCacheUtil.getCacheObject(Constant.FILE_CONFIG+path);
            if (fileConfigDTO == null || ObjectUtils.isEmpty(fileConfigDTO.getWhiteIpList())){
                log.info("该文件无须ip白名单校验");
                return false;
            }
            Assert.isTrue(IpUtil.isWhiteIp(fileContext.getFileRequest().getUserIp(), fileConfigDTO.getWhiteIpList()), "您无权访问该文件，请联系管理员授权");
            return false;
        }

        log.info("开始校验bucket ip白名单");

        if (StringUtils.isEmpty(fileContext.getBucketDTO().getWhiteIpList())){
            log.info("该桶无须ip白名单校验");
            return false;
        }
        Assert.isTrue(IpUtil.isWhiteIp(fileContext.getBucketDTO(), fileContext.getFileRequest()), "您无权访问，请联系管理员授权");
        System.out.println(fileContext.getBucketDTO().getWhiteIpList()+" 通过白名单");
        return false;
    }
}
