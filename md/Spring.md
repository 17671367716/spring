#### 动态代理Proxy

___

业务类

```java
public class UserServiceImpl_01 implements UserService {

    UserDao user = new UserDaoImpl_02();

    public void getUser() {
        user.getUser();
    }

    public void add() {
        System.out.println("add");
    }

    public void del() {
        System.out.println("del");
    }

    public void update() {
        System.out.println("update");
    }

    public void select() {
        System.out.println("select");
    }
}
```

___

代理类（实现方式一）

```java
public class MyProxy implements InvocationHandler{

    //  代理对象
    private Object object;
    public void setObject(Object object) {
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
```

使用

```java
    static void proxy_01(){
        UserServiceImpl user = new UserServiceImpl();
        UserProxy myProxy = new UserProxy(user);
        myProxy.setObject(user);
        UserService proxy =(UserService) myProxy.getProxy();
        proxy.add();
    }
```

___

代理类（实现方式二）

```java
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
```

使用

```java
    static void proxy_02(){
        UserServiceImpl_02 user = new UserServiceImpl_02();
        UserProxy_02 userProxy_02 = new UserProxy_02();
        userProxy_02.setObject(user);
        UserService bean = (UserService)userProxy_02.getBean();
        bean.update();
    }
```



