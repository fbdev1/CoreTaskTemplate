package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Petrov", (byte) 31);
        userService.saveUser("John", "Jackson", (byte) 25);
        userService.saveUser("Alex", "Kotov", (byte) 55);
        userService.saveUser("Ronald", "Washington", (byte) 68);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }

}
