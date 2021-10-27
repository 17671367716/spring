package com.example.springboot.utile;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Bean implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(Bean.applicationContext == null){
            Bean.applicationContext = applicationContext;
        }
        System.out.println("========ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象,applicationContext="+Bean.applicationContext+"========");
    }

    //  获取applicationContext
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    //  通过name获取bean
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

    public static <T> List<T> getBeans(Class<T> clazz){
        String[] beanNames = getApplicationContext().getBeanNamesForType(clazz);
        List<T> list = new ArrayList<>();
        if (beanNames.length<2)
            return null;
        for (int i = 1; i < beanNames.length; i++) {
            list.add((T) getBean(beanNames[i]));
        }
        return list;
    }
}
