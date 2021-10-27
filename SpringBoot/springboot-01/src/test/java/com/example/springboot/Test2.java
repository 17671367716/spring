package com.example.springboot;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.bean.Tes;
import com.example.springboot.service.TestService;
import org.apache.catalina.LifecycleListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
public class Test2 {

    List<Integer> list = new ArrayList<>();

    @Test
    public void syn() {
//        synchronized (list){
        Thread thread1 = new Thread(() -> {
            f1(list);
        });

        Thread thread2 = new Thread(() -> {
            f2(list);
        });
        thread1.start();
        thread2.start();

//            f3(list);
//        }


    }

//    private void f3(List<Integer> list) {
//        System.out.println("------------");
//        while (list.size()>0){
//            System.out.println(list.get(0));
//            list.remove(0);
//        }
//        System.out.println("------------");
//    }

    public void f1(List list){
        while (true){
            System.out.println("------f1------");
            list.add(list.size()+1);
            System.out.println( "f1 : " + list.get(list.size()-1));
        }
    }

    public void f2(List list){
        while (true){
            System.out.println("------f2------");
            if (list.size()>0){
                System.out.println( "f2 : " + list.get(0));
                list.remove(0);
            }
        }
    }

    @Test
    public void f3(){
        AtomicInteger atomicInteger = new AtomicInteger(10);
        atomicInteger.getAndAdd(5);

        System.out.println(atomicInteger);
    }


}
