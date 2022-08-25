package com.chengzzz.zcloud.exception;

/**
 * 路径异常
 *
 * @author Yet
 * @date 2022/08/22 21:20
 **/
public class PathErrorException extends Exception{
    public PathErrorException() {
        super();
    }

    public PathErrorException(String message) {
        super(message);
    }
}
