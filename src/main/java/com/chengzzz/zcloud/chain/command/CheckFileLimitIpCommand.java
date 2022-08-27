package com.chengzzz.zcloud.chain.command;

import com.chengzzz.zcloud.chain.FileContext;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.util.Assert;

import java.util.stream.Collectors;

/**
 * @author Yet
 * @date 2022/08/27 23:49
 **/
public class CheckFileLimitIpCommand implements Command {

    /**
     *
     * @param context The {@link Context} to be processed by this
     *  {@link Command}
     *
     * @return 是否停止执行责任链, true: 停止执行责任链, false: 继续执行责任链
     * @throws Exception
     */
    @Override
    public boolean execute(Context context) throws Exception {
        FileContext fileContext = (FileContext)context;
        long count = fileContext.getFiles().stream()
                .filter(
                        fileEntity -> fileEntity.getPath().equals(fileContext.getFileRequest().getPath()) && fileEntity.getLimitIp().contains(fileContext.getFileRequest().getUserIp()
                        ))
                .count();
        Assert.isTrue(count == 0, "您无权访问，请联系管理员授权");
        return false;
    }
}
