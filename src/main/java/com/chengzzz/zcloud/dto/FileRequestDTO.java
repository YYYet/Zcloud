package com.chengzzz.zcloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

/**
 * @author Yet
 * @date 2022/08/27 23:32
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileRequestDTO {
    @NonNull
    private String bucketId;
    private String password;
    @NonNull
    private String userIp;
    private String path;
    private String userOperation;
    private BucketDTO bucket;
}
