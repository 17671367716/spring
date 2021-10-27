package service.impl;

import org.springframework.stereotype.Service;
import service.UserService;

@Service
public class UserServiceImpl implements UserService {

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
