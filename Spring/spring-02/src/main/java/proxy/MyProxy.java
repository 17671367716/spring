package proxy;

import service.UserService;

public class MyProxy implements UserService{

    UserService userService;

    public MyProxy() { }

    public MyProxy(UserService userService) {
        this.userService = userService;
    }


    public void getUser() {
        logs();
        userService.getUser();
    }

    public void add() {
        logs();
        userService.add();
    }

    public void del() {
        logs();
        userService.del();
    }

    public void update() {
        logs();
        userService.update();
    }

    public void select() {
        logs();
        userService.select();
    }

    private void logs(){
        System.out.print("【 debug 】");
    }
}
