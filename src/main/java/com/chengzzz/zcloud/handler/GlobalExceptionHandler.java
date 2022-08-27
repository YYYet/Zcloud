package com.chengzzz.zcloud.handler;

import com.chengzzz.zcloud.constant.Constant;
import com.chengzzz.zcloud.constant.HttpStatusEnum;
import com.chengzzz.zcloud.responce.RestResponce;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public RestResponce<String> exceptionHandler(HttpServletRequest request, Exception e) {
        //绑定异常是需要明确提示给用户的
//        if (e instanceof Exception) {
//            BindException exception = (BindException) e;
//            List<ObjectError> errors = exception.getAllErrors();
//            String msg = errors.get(0).getDefaultMessage();//获取自错误信息
//            return RestResponce.fail(msg);//将具体
//        }
// 其余异常简单返回为服务器异常
        return RestResponce.fail(e.getMessage());

    }
}
