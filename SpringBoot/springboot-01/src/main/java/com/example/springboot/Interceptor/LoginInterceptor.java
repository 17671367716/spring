package com.example.springboot.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springboot.bean.User;
import com.example.springboot.utile.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    //  目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(" ======   preHandle   ======");
        log.info(" ======   请求url : {}  ======",request.getRequestURI());
        HttpSession session = request.getSession();
        String token = request.getHeader("token");
        DecodedJWT decodedJWT = JWTUtils.decodedJWT(token);
        Claim claim = decodedJWT.getClaim("user");
        User user = JSONObject.parseObject(claim.asString(),User.class);
        User loginUser = (User)session.getAttribute("loginUser");
        return true;
        /* if ( loginUser != null){
            log.info(" ======   用户已登录   ======");
            log.info(" ======   用户名：{}，密码：{}   ======",loginUser.getUsername(),loginUser.getPassword());
            return true;
        }
        return false;*/
    }

    //  目标方法执行完成之后
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info(" ======   postHandle   ======");
        log.info(" ======   请求url : {}  ======",request.getRequestURI());
    }

    //  页面渲染后
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info(" ======   afterCompletion   ======");
        log.info(" ======   请求url : {}  ======",request.getRequestURI());
    }
}
