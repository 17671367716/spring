package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.bean.User;

/**
* @Entity com.example.springboot.bean.User
*/
public interface UserMapper extends BaseMapper<User> {


    User getUserByUsername(String username);
}
