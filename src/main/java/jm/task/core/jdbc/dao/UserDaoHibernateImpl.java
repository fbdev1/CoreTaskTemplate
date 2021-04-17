package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import java.util.List;
public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            NativeQuery<User> query = session.createSQLQuery("CREATE TABLE `mydbtest`.`users`(\n" +
                    "  `id`INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name`VARCHAR(45) NOT NULL,\n" +
                    "  `age`INT(3) NULL,\n" +
                    "  `lastName`VARCHAR(45) NULL,\n" +
                    "                PRIMARY KEY(`id`))\n" +
                    "        ENGINE = InnoDB\n" +
                    "        DEFAULT CHARACTER SET = utf8;");
            query.executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");
        } catch (Exception e) {
            System.err.println("Ошибка при создании таблицы");
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            Query<User> query = session.createSQLQuery("drop table users");
            query.executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            System.err.println("Ошибка при удалении таблицы: ");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных.");
        } catch (HibernateException e) {
            System.err.println("Не удалось добавить пользователея");
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            User user1 = session.get(User.class, id);
            if (user1 != null) {
                session.delete(user1);
                System.out.println("User " + id + " удален");
            } else {
                System.err.println("User " + id + " не найден");
            }
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Ошибка при удалении пользователя");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        try (Session session = Util.getSessionFactory().openSession()) {

            Query<User> query = session.createQuery("select e from User e");
            list = query.list();
        } catch (Exception e) {
            System.err.println("Не удалось получить получить список всех пользователей");
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            Query<User> query = session.createQuery("delete from User");
            query.executeUpdate();
            transaction.commit();
            System.out.println("Все записи удалены");
        } catch (Exception e) {

            System.err.println("Не удалось удалить всех пользователей");
        }
    }
}

