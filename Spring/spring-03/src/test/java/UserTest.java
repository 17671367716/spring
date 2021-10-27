import service.UserService;
import service.impl.UserServiceImpl;

public class UserTest {

    public static void main(String[] args) {
        UserService user = new UserServiceImpl();
        user.add();
        user.update();
        user.select();
        user.del();
    }
}
