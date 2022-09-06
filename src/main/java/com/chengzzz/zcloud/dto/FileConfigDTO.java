package com.chengzzz.zcloud.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Yet
 * @date 2022/09/01 23:45
 **/
@Data
public class FileConfigDTO {
    private List<String> whiteIpList;
    private List<String> password;
    private List<String> whiteUserList;
}
