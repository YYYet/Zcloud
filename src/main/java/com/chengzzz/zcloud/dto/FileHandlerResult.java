package com.chengzzz.zcloud.dto;

import com.chengzzz.zcloud.entity.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yet
 * @date 2022/08/28 12:26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileHandlerResult {
    private Map files;
    private BucketDTO bucket;
    private String spendTime;
    private String completeTime;
}
