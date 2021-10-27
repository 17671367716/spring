package com.example.springboot;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.bean.Tes;
import com.example.springboot.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sun.misc.GC;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class Tests {

    @Autowired
    TestService testService;


    @Test
    public void set(){
        ArrayList<Tes> list = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            String l = System.currentTimeMillis()+"";
            Tes tes = new Tes(l,l,l);
            list.add(tes);
        }

        testService.saveOrUpdateBatch(list);
//        int count = testService.count();
//        System.out.println(count);
    }

    @Test
    public void count(){
        long start = System.currentTimeMillis();
        QueryWrapper<Tes> wrapper = new QueryWrapper<>();
        wrapper.likeRight("a","17");
        List<Tes> list = testService.list(wrapper);
        list.forEach(data->{
            System.out.println(data.toString());
        });
        long end = System.currentTimeMillis();
        System.out.println(start-end);
    }

    @Test
    public void f1(){
        ArrayList<Object> list = new ArrayList<>();
        synchronized (list){
            for (int i = 0; i < 100; i++) {
                list.add(i);
            }
        }
    }

}
