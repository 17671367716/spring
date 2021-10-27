package com.example.springboot;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.bean.User;
import com.example.springboot.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    UserService userService;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void set(){
        redisTemplate.opsForValue().set("myKey","阿萨大大");
        redisTemplate.opsForValue().set("name","zhangsan");
        redisTemplate.opsForValue().set("zhangsan","张三");

        System.out.println(redisTemplate.opsForValue().get("myKey"));
        System.out.println(redisTemplate.opsForValue().get("name"));
        System.out.println(redisTemplate.opsForValue().get("zhangsan"));
        System.out.println(redisTemplate.opsForValue().get("my_key"));
        Set keys = redisTemplate.keys("*");
        keys.stream().forEach(System.out::println);
    }

    @Test
    public void keys(){
//        redisTemplate.opsForValue().set("my_key","my_value");
//        System.out.println(redisTemplate.opsForValue().get("my_key"));
        Set keys = redisTemplate.keys("*");
        keys.stream().forEach(System.out::println);
    }


    @Test
    public void del(){
//        redisTemplate.opsForValue().set("my_key","my_value");
//        System.out.println(redisTemplate.opsForValue().get("my_key"));
        redisTemplate.delete("name");

    }

    @Test
    public void setBean(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.lambda()
                .eq(User::getUsername,"zhangsan")
                .last("limit 1");
        User user = userService.getOne(userQueryWrapper);
        redisTemplate.opsForValue().set("user",user);
        User u =(User) redisTemplate.opsForValue().get("user");
        System.out.println(u.toString());

    }

    @Test
    public void a(){
        assertEquals(1+3,4);
        assertSame(new User(),new User());
    }

    @Test
    public void b(){
        Collection slaves = redisTemplate.opsForCluster().getSlaves(
                new RedisClusterNode("47.96.174.23", 6371));
        slaves.forEach(System.out::println);
    }
}
