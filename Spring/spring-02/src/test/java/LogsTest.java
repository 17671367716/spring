import proxy.MyProxy;
import service.UserService;
import service.impl.UserServiceImpl_01;

public class LogsTest {
    public static void main(String[] args) {
        UserService user =  new UserServiceImpl_01();
        MyProxy proxy = new MyProxy(user);
        proxy.add();
        proxy.select();
    }
}
