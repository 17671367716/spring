package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.bean.Tes;
import com.example.springboot.service.TestService;
import com.example.springboot.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
*
*/
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Tes> implements TestService{
    @Autowired
    TestMapper testMapper;
}
