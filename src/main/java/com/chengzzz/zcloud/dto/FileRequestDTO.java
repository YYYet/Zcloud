package com.chengzzz.zcloud.dto;

import lombok.Data;
import org.springframework.lang.NonNull;

/**
 * @author Yet
 * @date 2022/08/27 23:32
 **/
@Data
public class FileRequestDTO {
    @NonNull
    private String bucketId;
    private String password;
    @NonNull
    private String userIp;
    private String path;
    private String userOperation;
}
