package com.example.springboot.utile;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.example.springboot.enume.ErrorMessage;
import com.example.springboot.exception.CustomExceptionHandler;
import com.example.springboot.exception.GlobalExceptionHandler;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashMap;

@Data
@Component
@ConfigurationProperties(prefix="jwt")
public class JWTUtils {

    public static String secret;

    public void setSecret(String secret) {
        JWTUtils.secret = secret;
    }

    /**
     * 获取TOKEN
     * @param obj
     * @return
     */
    public static String getToken(Object obj){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,30);
//        System.out.println(usr.toString());

        return  JWT.create().withHeader(new HashMap<>())
                .withClaim("user", JSONObject.toJSONString(obj)) // 添加对象
                .withExpiresAt(calendar.getTime())  //  设置过期事件
                .sign(Algorithm.HMAC256(secret));  // 签名
    }

    /**
     * 验证TOKEN
     * @param token
     * @return
     */
    public static DecodedJWT decodedJWT(String token){
//        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
//        DecodedJWT decodedJWT = jwtVerifier.verify(token);
//        return decodedJWT;
        try {
            return JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        }catch (TokenExpiredException e){
            throw new CustomExceptionHandler(ErrorMessage.TOKEN_TIME_OUT.getCode(),ErrorMessage.TOKEN_TIME_OUT.getMsg());
        }catch (Exception e){
            throw new CustomExceptionHandler(ErrorMessage.TOKEN_ERROR.getCode(),ErrorMessage.TOKEN_ERROR.getMsg());
        }
    }
}
