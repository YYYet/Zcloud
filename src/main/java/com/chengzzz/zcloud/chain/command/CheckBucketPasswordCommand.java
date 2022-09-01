package com.chengzzz.zcloud.chain.command;

import com.chengzzz.zcloud.chain.FileContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author Yet
 * @date 2022/08/27 23:58
 **/
@Service
@Slf4j
public class CheckBucketPasswordCommand implements Command {
    @Override
    public boolean execute(Context context) throws Exception {
        log.info("开始校验bucket密码");
        FileContext fileContext = (FileContext)context;
        if (StringUtils.isEmpty(fileContext.getBucketDTO().getPassword())){
            log.info("无需密码控制");
            return false;
        }
        Assert.isTrue(fileContext.getBucketDTO().getPassword().equals(fileContext.getFileRequest().getPassword()), "密码错误");
        log.info(fileContext.getBucketDTO().getPassword()+" bucket密码正确");
        return false;
    }
}
