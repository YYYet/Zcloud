package com.chengzzz.zcloud.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应
 *
 * @author Yet
 * @date 2022/08/23 20:26
 **/

@Data
@AllArgsConstructor
public class BaseResponce<T> implements Serializable {
    Integer code;
    String message;
    T data;
}
