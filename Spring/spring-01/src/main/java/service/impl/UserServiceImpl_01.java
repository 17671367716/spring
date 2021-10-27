package service.impl;

import Dao.UserDao;
import Dao.UserDaoImpl_02;
import service.UserService;

public class UserServiceImpl_01 implements UserService {

    UserDao user = new UserDaoImpl_02();

    public void getUser() {
        user.getUser();
    }
}
