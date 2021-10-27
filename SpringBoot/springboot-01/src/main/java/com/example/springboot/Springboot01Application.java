package com.example.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

@MapperScan(basePackages = {"com.example.springboot.mapper"})
@ServletComponentScan(basePackages = "*")
@SpringBootApplication
public class Springboot01Application {

	public static void main(String[] args) {

		ConfigurableApplicationContext run = SpringApplication.run(Springboot01Application.class, args);
		String[] names = run.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println( " ------  " + name);
		}
//
//
//		System.out.println("------");
//		String[] beanNamesForType = run.getBeanNamesForType(User.class);
//		for (String s : beanNamesForType) {
//			System.out.println(s);
//		}
//
//		boolean zhangsan = run.containsBean("zhangsan");
//		boolean lisi = run.containsBean("lisi");
//		System.out.println(zhangsan);
//		System.out.println(lisi);

	}

}
