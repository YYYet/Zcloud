package com.chengzzz.zcloud.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * @author Yet
 * @date 2022/08/27 20:19
 **/

@Data
@Getter
public class BucketDTO {
 private String bucketId;
 private String password;
 private List<String> whiteIpList;
 private String path;
 private Boolean needPassword;

}
