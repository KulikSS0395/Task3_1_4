package ru.jm.ds;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.jm.ds.configuration.MyConfig;
import ru.jm.ds.entity.User;

import java.util.List;

public class App 
{
    public static void main( String[] args ) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        Communication communication = context.getBean("communication", Communication.class);

        List<User> allUsers = communication.getUsers();
        System.out.println(allUsers);

        User user = new User((long)3,"James", "Brown", (byte) 24);
        User userUpdate = new User((long)3,"Thomas", "Shelby", (byte) 24);

        communication.saveUser(user);

        communication.updateUser(userUpdate);

        communication.deleteUser(3);
    }
}
