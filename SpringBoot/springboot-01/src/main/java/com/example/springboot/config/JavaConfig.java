package com.example.springboot.config;

import com.example.springboot.bean.Dog;
import com.example.springboot.bean.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

@ImportResource
@Configuration()
@Import({User.class})
@EnableConfigurationProperties(Dog.class)
public class JavaConfig {

//    @ConditionalOnBean(name = "zhangsan")
    @Bean(name = "ls")
    public User lisi(){
        return new User("lisi",20,"lisi","123456","a");
    }

    @ConditionalOnBean(name = "ls")
    @Bean
    public User zhangsan(){
        return new User("zhangsan",18,"zhangsan","123456","b");
    }

}
