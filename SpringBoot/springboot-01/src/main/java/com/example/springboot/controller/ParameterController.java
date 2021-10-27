package com.example.springboot.controller;

import com.example.springboot.bean.User;
import com.example.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/parameter")
public class ParameterController {

    @Autowired
    UserService userService;

    @RequestMapping("/getUser/{school}/{name}/abc")
    public Map pathVariable(@PathVariable(name = "school") String school,
                                       @PathVariable(name = "name") String name,
                                       @PathVariable Map<String,String> map){
        HashMap<String, Object> responce = new HashMap<>();
        responce.put("school",school);
        responce.put("name",name);
        responce.put("map",map);
        return responce;
    }

    @RequestMapping("/requestHeader")
    public Map requestHeader(@RequestHeader Map<String,String> map,
                             @RequestHeader("Host") String host){
        HashMap<String, Object> responce = new HashMap<>();
        responce.put("map",map);
        responce.put("host",host);
        return responce;
    }

    @RequestMapping("/requestParam")
    public Map requestParam(@RequestParam("name") String name,
                            @RequestParam("inters") String inters,
                            @RequestParam("inters") List list,
                            @RequestParam Map<String,String> map){
        HashMap<String, Object> responce = new HashMap<>();
        responce.put("name",name);
        responce.put("inters",inters);
        responce.put("list",list);
        responce.put("map",map);
        return responce;
    }

    @RequestMapping("/requestBody")
    public User requestBody(@RequestBody User user){
        return user;
    }

    @RequestMapping("/matrixVariable/{zhangsan}/{lisi}")
    public Map matrixVariable(@MatrixVariable(value = "age",pathVar = "zhangsan") Integer age,
                              @MatrixVariable(value = "inters",pathVar = "lisi") List inters){
        Map<String, Object> responce = new HashMap<>();
        responce.put("zhangsan",age);
        responce.put("lisi",inters);
        return responce;
    }

    @GetMapping("/error")
    public String _500Error(){
        int i = 1/ 0;
        return "error";
    }

}
