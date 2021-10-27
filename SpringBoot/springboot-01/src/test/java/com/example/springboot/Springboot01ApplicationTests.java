package com.example.springboot;

import com.example.springboot.bean.Dog;
import com.example.springboot.bean.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class Springboot01ApplicationTests {

	@Autowired
	Dog dog;

	@Test
	void contextLoads() {
//		User zhangsan = (User)Bean.getBean("zhangsan");
//		User lisi = (User)Bean.getBean("lisi");
//		System.out.println(zhangsan.toString());
//		System.out.println(lisi.toString());

	}

	@Test
	void configurationProperties(){
		System.out.println(dog);
	}

	@Test
	void maopao(){

//		int [] arr = {2,6,5,4,9,8,3,1,7};
//		int [] arr = {5,3,2,1,4,6,9,7,8};
		int [] arr = {9,8,7,6,5,4,3,2,1};
		for (int i = 8;i >= 0; i--) {
			for (int j = 0;j<i ;j++){
				if(arr[j]> arr[j+1]){
					int x = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = x;
				}
			}
			Arrays.stream(arr).forEach(System.out::print);
			System.out.println();
		}
//		System.out.println(arr);

	}

	@Test
	void f1(){
		User user = new User();
		Dog dog = new Dog();
		List<Object> f = f(user,dog);
		System.out.println(f.get(0).getClass());
	}

	public <A,E>  List<A> f(A t,E e){
		System.out.println(t.getClass());
		List<A> list = new ArrayList<>();
		list.add(t);
		return list;
	}
}
