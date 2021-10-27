package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.bean.Account;
import com.example.springboot.bean.User;
import com.example.springboot.service.AccountService;
import com.example.springboot.service.UserService;
import com.example.springboot.utile.Result;
import com.example.springboot.utile.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    UserService userService;
    @Autowired
    AccountService accountService;

    @RequestMapping("/login")
    public Result login(@RequestBody User user){
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(User::getUsername,user.getUsername());
        User usr = userService.getOne(queryWrapper, false);
        if (usr == null ){
            return ResultGenerator.genFailResult("查无此人");
        }
        System.out.println(usr.toString());
        return ResultGenerator.genSuccessResult(usr);
    }

    @RequestMapping("/getUserByUsername")
    public Result getUserByUsername(@RequestParam(name = "username") String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(User::getUsername,username);
//                .last("LIMIT 1");
        List<User> list = userService.list(queryWrapper);
        return ResultGenerator.genSuccessResult(list);
    }

    @RequestMapping("/insert")
    public Result insert(@RequestBody Account account) {
        System.out.println(account.toString());
        accountService.save(account);
        if (account.getIphone().length() == 0){
            int i = 1/0;
        }
        return ResultGenerator.genSuccessResult();
    }

}
