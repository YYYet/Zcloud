package com.chengzzz.zcloud.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author Yet
 * @date 2022/08/27 20:19
 **/

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BucketDTO {
 private String bucketId;
 private String password;
 private String limitIp;
 private String path;
}
