package com.fekpal.web.handler;

import com.fekpal.common.constant.ResponseCode;
import com.fekpal.common.json.JsonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by APone on 2018/2/21 22:23.
 * 全局异常处理器，捕获控制层的异常并做统一处理返回
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 自定义异常处理
     *
     * @param e 自定义异常封装
     * @return json封装
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public JsonResult<List> customExceptionHandler(Exception e) {
        e.printStackTrace();
        JsonResult<List> result = new JsonResult<>();
        result.setStateCode(ResponseCode.RESPONSE_ERROR, e.getMessage());
        return result;
    }
}
