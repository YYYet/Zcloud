package com.chengzzz.zcloud.chain.command;

import com.chengzzz.zcloud.chain.FileContext;
import com.chengzzz.zcloud.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author Yet
 * @date 2022/08/27 23:19
 **/
@Service
@Slf4j
public class CheckBucketLimitIpCommand implements Command {
    @Override
    public boolean execute(Context context) throws Exception {
        log.info("开始校验bucket ip白名单");
        FileContext fileContext = (FileContext)context;
        if (StringUtils.isEmpty(fileContext.getBucketDTO().getWhiteIpList())){
            log.info("无须ip白名单校验");
            return false;
        }
        System.out.println(IpUtil.isWhiteIp(fileContext.getBucketDTO(), fileContext.getFileRequest()));
        Assert.isTrue(IpUtil.isWhiteIp(fileContext.getBucketDTO(), fileContext.getFileRequest()), "您无权访问，请联系管理员授权");
        return false;
    }
}
