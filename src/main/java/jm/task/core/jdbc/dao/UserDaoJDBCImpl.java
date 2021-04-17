package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

   private static Connection connection = Util.getMySQLConnection();

    public void createUsersTable() {
        try (Statement st = connection.createStatement()) {
            st.executeUpdate("CREATE TABLE `mydbtest`.`users`(\n" +
                    "  `id`INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name`VARCHAR(45) NOT NULL,\n" +
                    "  `age`INT(3) NULL,\n" +
                    "  `lastName`VARCHAR(45) NULL,\n" +
                    "                PRIMARY KEY(`id`))\n" +
                    "        ENGINE = InnoDB\n" +
                    "        DEFAULT CHARACTER SET = utf8;");
            if (st.execute("SHOW TABLES FROM `mydbtest` like 'user';")) {
                System.out.println("Таблица создана");
            }


        } catch (SQLException e) {
            System.err.println("Не удалось создать новую таблицу");
        }
    }

    public void dropUsersTable() {
        try (Statement st = connection.createStatement()) {
            st.execute("drop table users;");
            if (st.execute("SHOW TABLES FROM `mydbtest` like 'user';")) {
                System.out.println("Таблица удалена");
            }
        } catch (SQLException e) {
            System.err.println("Не удалось удалить таблицу");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String zapros = "insert into users (name, lastName, age) values ('" + name + "', '" + lastName + "', " + age + ")";
        try (Statement st = connection.createStatement()) {

            if (!st.execute(zapros)) {
                System.out.println("User с именем – "+name+" добавлен в базу данных.");
            }
        } catch (SQLException e) {
            System.err.println("Не удалось добавить пользователея");
        }
    }

    public void removeUserById(long id) {
        try (Statement st = connection.createStatement()) {
            if (!st.execute("delete from users where id =" + id)) {
                System.out.println("Запись удалена");
            }
        } catch (SQLException e) {
            System.err.println("Не удалось удалить пользователя");
        }
    }

    public List<User> getAllUsers() {
        List<User> listOfUsers = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet resultSet = st.executeQuery("select * from users");
            while (resultSet.next()) {
                User user = new User();
                user.setId((long) resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge((byte) resultSet.getInt("age"));
                listOfUsers.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Не удалось получить получить список всех пользователей");
        }
        return listOfUsers;
    }

    public void cleanUsersTable() {
        try (Statement st = connection.createStatement()) {
            if (!st.execute("delete from users")) {
                System.out.println("Все записи удалены");
            }
        } catch (SQLException e) {
            System.err.println("Не удалось удалить всех пользователей");
        }
    }
}
