package service.impl;

import Dao.UserDao;
import service.UserService;

public class UserServiceImpl_02 implements UserService {

    UserDao user ;

    public void setUser(UserDao user) {
        this.user = user;
    }

    public void getUser() {
        user.getUser();
    }

    public void add() {
        System.out.println("1");
    }

    public void del() {
        System.out.println("2");
    }

    public void update() {
        System.out.println("3");
    }

    public void select() {
        System.out.println("4");
    }
}
