package com.example.springboot.exception;

import com.example.springboot.enume.ErrorMessage;
import com.example.springboot.responce.Responce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.ServiceNotFoundException;
import javax.servlet.http.HttpServletRequest;

/**
 * 自定义全局异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler{

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = CustomExceptionHandler.class)
    @ResponseBody
    public Responce bizExceptionHandler(HttpServletRequest req, CustomExceptionHandler e){
        logger.error("发生业务异常！原因是：{}",e.getMsg());
        return Responce.error(e.getCode(),e.getMsg());
    }

    /**
     * 处理空指针的异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public Responce exceptionHandler(HttpServletRequest req, NullPointerException e){
        logger.error("发生空指针异常！原因是:",e);
        return Responce.error(ErrorMessage.BODY_NOT_MATCH);
    }

    /**
     * 处理其他异常
     * @param req
     * @param e
     * @return
     */
//    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Responce exceptionHandler(HttpServletRequest req, Exception e){
        logger.error("未知异常！原因是:",e);
        return Responce.error(ErrorMessage.INTERNAL_SERVER_ERROR);
    }

}
