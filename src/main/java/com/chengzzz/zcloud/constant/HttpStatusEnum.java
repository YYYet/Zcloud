package com.chengzzz.zcloud.constant;


import cn.hutool.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@Getter
@AllArgsConstructor
public enum HttpStatusEnum {

    HTTP_OK(HttpStatus.HTTP_OK, Constant.HTTP_OK),
    HTTP_FAIL(HttpStatus.HTTP_BAD_REQUEST, Constant.HTTP_BAD_REQUEST);

    Integer code;
    String message;

}
