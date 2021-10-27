package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.bean.User;
import com.example.springboot.service.UserService;
import com.example.springboot.utile.Result;
import com.example.springboot.utile.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserService userService;

    @GetMapping("/getAll")
    public List<User> getAll(){

        log.warn("getAll....");
        return userService.list();
    }

    @GetMapping
    public String get(){
        return "get user";
    }

    @PostMapping
    public String save(){
        return "save user";
    }

    @PutMapping
    public String update(){
        return "update user";
    }

    @DeleteMapping
    public String delete(){
        return "delete user";
    }

    @RequestMapping("/getAllUserNum")
    public String getAllUserNum(){
        int count = userService.count();
        return count+"";
    }

    @RequestMapping("/getUserByUsername/{username}")
    public User getUserByUsername(@PathVariable String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.lambda()
                .eq(User::getUsername,username);
//                .last("LIMIT 1");
        return userService.getOne(queryWrapper,false);
    }

    @RequestMapping("/page")
    public Page page(@RequestParam(value = "page" ,defaultValue = "1" ) Integer page,
                     @RequestParam(value = "size" ,defaultValue = "5" ) Integer size){
        Page<User> userPage = new Page<>(page,size);
        Page<User> responce = userService.page(userPage,null);
        List<User> records = responce.getRecords();
        return responce;
    }

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


//    @ApiOperation(value = "修改角色信息", notes = "修改角色信息")
    @PostMapping("/switch")
    public String search(@RequestParam(value = "id",defaultValue = "") String id,
                           @RequestParam(value = "name",defaultValue = "") String name,
                           @RequestParam(value = "abc",defaultValue = "") String age) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(User::getId,id)
                .like(User::getName,name)
                .like(User::getAge,age);
        List<User> list = userService.list(queryWrapper);
        return "";
    }
}
