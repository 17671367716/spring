
import Config.JavaConfig;
import Entity.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class UserTest {

    public static void main(String[] args) {

//        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
//        User user = (User) context.getBean("getUser");
////        user.setName("qwe");
//        System.out.println(user.toString());

        Single single = Single.getSingle();
        System.out.println(System.identityHashCode(single));
        Single single1 = Single.getSingle();
        System.out.println(System.identityHashCode(single1));
    }

}
