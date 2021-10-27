import Config.UserProxy;
import Config.UserProxy_02;
import proxy.MyProxy;
import service.UserService;
import service.impl.UserServiceImpl_01;
import service.impl.UserServiceImpl_02;

public class ProxyTest {

    public static void main(String[] args) {
        proxy_02();
//        proxy_02();
        proxy_01();
//        proxy_01();
    }

    static void proxy_01(){
        UserServiceImpl_01 user = new UserServiceImpl_01();
        UserProxy myProxy = new UserProxy(user);
//        myProxy.setObject(user);
        UserService proxy =(UserService) myProxy.getProxy();
//        System.out.println(System.identityHashCode(proxy));
        proxy.add();
//        proxy.select();
    }

    static void proxy_02(){
        UserServiceImpl_02 user = new UserServiceImpl_02();
        UserProxy_02 userProxy_02 = new UserProxy_02();
        userProxy_02.setObject(user);
        UserService bean = (UserService)userProxy_02.getBean();
        bean.update();
    }
}
