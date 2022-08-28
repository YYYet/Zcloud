package com.chengzzz.zcloud.responce;

import com.chengzzz.zcloud.constant.HttpStatusEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一响应
 *
 * @author Yet
 * @date 2022/08/23 20:26
 **/


@Data
@NoArgsConstructor
public class RestResponce<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    public RestResponce(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static RestResponce sucess(){
        return new RestResponce(HttpStatusEnum.HTTP_OK.getCode(), HttpStatusEnum.HTTP_OK.getMessage(), null);
    }

    public static RestResponce fail(){
        return new RestResponce(HttpStatusEnum.HTTP_FAIL.getCode(), HttpStatusEnum.HTTP_FAIL.getMessage(), null);
    }


    public static RestResponce sucess(Object data){
        return new RestResponce(HttpStatusEnum.HTTP_OK.getCode(), HttpStatusEnum.HTTP_OK.getMessage(), data);
    }

    public static RestResponce fail(Object data){
        return new RestResponce(HttpStatusEnum.HTTP_FAIL.getCode(), HttpStatusEnum.HTTP_FAIL.getMessage(), data);
    }

    @Override
    public String toString() {
        return "RestResponce{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
