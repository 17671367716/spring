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
}
