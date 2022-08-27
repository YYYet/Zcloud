package com.chengzzz.zcloud.chain.command;

import com.chengzzz.zcloud.chain.FileContext;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.util.Assert;

/**
 * @author Yet
 * @date 2022/08/27 23:58
 **/
public class CheckBucketPasswordCommand implements Command {
    @Override
    public boolean execute(Context context) throws Exception {
        FileContext fileContext = (FileContext)context;
        Assert.isTrue(fileContext.getBucketDTO().getPassword().equals(fileContext.getFileRequest().getPassword()), "密码错误");
        return false;
    }
}
