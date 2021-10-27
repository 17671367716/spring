package service.impl;

import Dao.UserDao;
import Dao.UserDaoImpl_02;
import service.UserService;

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
