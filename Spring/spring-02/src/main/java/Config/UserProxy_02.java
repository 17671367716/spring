package Config;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class UserProxy_02 {

    private Object object;

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getBean() {
        InvocationHandler h = new InvocationHandler() {

            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object invoke = method.invoke(object, args);
                return invoke;
            }
        };
        logs();
        Object o = Proxy.newProxyInstance(this.getClass().getClassLoader(), object.getClass().getInterfaces(), h);
        return o;
    }

    private void logs(){
        System.out.print(" [ debug ] ");
    }
}

