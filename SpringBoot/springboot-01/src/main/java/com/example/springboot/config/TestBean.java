package com.example.springboot.config;

import com.example.springboot.bean.Dog;
import com.example.springboot.bean.Pig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

//@Configuration(proxyBeanMethods = false)
@Configuration
public class TestBean {

    @Bean
    public Pig beanPig(){
        Pig pig = new Pig();
        System.out.println("=======     "+pig+"     =======");
        return pig;
    }

    public Pig getPig(){
        return beanPig();
    }

}
