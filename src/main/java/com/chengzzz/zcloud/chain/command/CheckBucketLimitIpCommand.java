package com.chengzzz.zcloud.chain.command;

import com.chengzzz.zcloud.chain.FileContext;
import com.chengzzz.zcloud.utils.IpUtil;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.util.Assert;

/**
 * @author Yet
 * @date 2022/08/27 23:19
 **/
public class CheckBucketLimitIpCommand implements Command {
    @Override
    public boolean execute(Context context) throws Exception {
        FileContext fileContext = (FileContext)context;
        Assert.isTrue(IpUtil.hasLimitIp(fileContext.getBucketDTO(), fileContext.getFileRequest()), "您无权访问，请联系管理员授权");
        return false;
    }
}
