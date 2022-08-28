package com.chengzzz.zcloud.chain.command;

import com.chengzzz.zcloud.chain.FileContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

/**
 * @author Yet
 * @date 2022/08/27 23:49
 **/
@Service
@Slf4j
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
        log.info("开始校验文件ip白名单");
        FileContext fileContext = (FileContext)context;
        if (StringUtils.isEmpty(fileContext.getBucketDTO().getWhiteIpList())){
            log.info("无须ip白名单校验");
            return false;
        }
        long count = fileContext.getFiles().stream()
                .filter(
                        fileEntity -> fileEntity.getPath().equals(fileContext.getFileRequest().getPath()) && fileEntity.getWhiteIpList().contains(fileContext.getFileRequest().getUserIp()
                        ))
                .count();
        Assert.isTrue(count == 0, "您无权访问，请联系管理员授权");
        return false;
    }
}
