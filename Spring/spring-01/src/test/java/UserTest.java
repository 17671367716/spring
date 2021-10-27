import Dao.UserDaoImpl_01;
import Dao.UserDaoImpl_02;
import service.UserService;
import service.impl.UserServiceImpl_01;
import service.impl.UserServiceImpl_02;

public class UserTest {

    public static void main(String[] args) {

        UserServiceImpl_02 user = new UserServiceImpl_02();
        user.setUser(new UserDaoImpl_01());
        user.getUser();
    }

}
