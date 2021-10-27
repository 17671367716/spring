package Entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class User {

    String name;

    public String getName() {
        return name;
    }

    @Value("132")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
