package com.example.springboot.responce;

import com.example.springboot.enume.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Responce {
    /**
     * 响应代码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应结果
     */
    private Object result;

    public static Responce sucess(Object data){
        return new Responce(
                ErrorMessage.SUCCESS.getCode(),
                ErrorMessage.SUCCESS.getMsg(),
                data);
    }

    public static Responce error(Object data){
        return new Responce(
                ErrorMessage.INTERNAL_SERVER_ERROR.getCode(),
                ErrorMessage.INTERNAL_SERVER_ERROR.getMsg(),
                data);
    }

    public static Responce error(Integer code,String message){
        return new Responce(
                code,
                message,
                null);
    }
}
