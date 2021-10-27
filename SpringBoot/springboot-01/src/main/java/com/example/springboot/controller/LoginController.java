package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.bean.User;
import com.example.springboot.service.UserService;
import com.example.springboot.utile.Result;
import com.example.springboot.utile.ResultGenerator;
import com.example.springboot.utile.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public Result login(@RequestBody User user){
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(User::getUsername,user.getUsername());
        User usr = userService.getOne(queryWrapper, false);
        if (usr == null ){
            return ResultGenerator.genFailResult("查无此人");
        }
        String token = JWTUtils.getToken(usr);
        return ResultGenerator.genSuccessResult(token);
    }

}
