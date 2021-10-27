package Config;


import proxy.MyProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class UserProxy implements InvocationHandler{

    //  代理对象
    private Object object;

    public void setObject(Object object) {
        this.object = object;
    }

    public UserProxy() {
    }

    public UserProxy(Object object) {
        this.object = object;
    }

    //  获得代理对象
    public Object getProxy(){
        return Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                this
        );
    }

    //  处理代理实例，返回对象
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logs(method.getName());
        Object o = method.invoke(object, args);
        return o;
    }

    // 业务处理
    private void logs(String name){
        System.out.print(" [ "+name+" ] ");
    }
}
