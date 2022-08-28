package com.chengzzz.zcloud.chain;

import com.chengzzz.zcloud.chain.command.CheckBucketLimitIpCommand;
import com.chengzzz.zcloud.chain.command.CheckBucketPasswordCommand;
import com.chengzzz.zcloud.chain.command.CheckFileLimitIpCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.chain.impl.ChainBase;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author Yet
 * @date 2022/08/27 21:35
 **/
@Service("FileChain")
@Slf4j
public class FileChain extends ChainBase {

    @Resource
    CheckBucketLimitIpCommand checkBucketLimitIpCommand;
    @Resource
    CheckBucketPasswordCommand checkBucketPasswordCommand;
    @Resource
    CheckFileLimitIpCommand checkFileLimitIpCommand;

    @PostConstruct
    public void init() {
        this.addCommand(checkBucketLimitIpCommand);
        this.addCommand(checkBucketPasswordCommand);
//        this.addCommand(checkFileLimitIpCommand);
    }

    public FileContext execute(FileContext context) throws Exception {
       super.execute(context);
       return context;
    }
}
