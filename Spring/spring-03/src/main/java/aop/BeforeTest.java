package aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BeforeTest {

    @Before("execution(* service.*.del(..))")
    public void before(){
        System.out.println("****before****");
    }

}
