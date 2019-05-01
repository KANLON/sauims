package com.fekpal.web.handler;

import com.fekpal.common.constant.ResponseCode;
import com.fekpal.common.json.JsonResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
    public JsonResult<Map<String, String>> customExceptionHandler(Exception e, Locale locale) {
        e.printStackTrace();
        JsonResult<Map<String, String>> result = new JsonResult<>();
        //处理验证错误，MethodArgumentNotValidException,不用再需要在controller加上BindingResult、Locale参数和判断
        if( e instanceof MethodArgumentNotValidException){
            HashMap<String, String> messagesData = new HashMap<>(16);
            List<FieldError> fieldErrors=((MethodArgumentNotValidException)e).getBindingResult().getFieldErrors();
            for (FieldError error:fieldErrors){
                messagesData.put(error.getField(),error.getDefaultMessage());
            }
            result.setStateCode(ResponseCode.RESPONSE_ERROR, messagesData.toString());
            return result;
        }
        result.setStateCode(ResponseCode.RESPONSE_ERROR, e.getMessage());
        return result;
    }
}
