package jm.task.core.jdbc.util;

//import com.mysql.cj.Session;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
//import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry standardServiceRegistry;

    private static String hostName = "localhost";
    private static String dbName = "mydbtest";
    private static String userName = "root";
    private static String password = "pak4795";
    private static String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?autoReconnect=true&useSSL=false";


    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();


                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, connectionURL);
                settings.put(Environment.USER, userName);
                settings.put(Environment.PASS, password);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

//                settings.put(Environment.SHOW_SQL, "true");
//
//                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
//
//                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

//    private static SessionFactory getSessionFactory(String connectionURL, String userName, String password) {
//        if (sessionFactory == null) {
//            try {
//                StandardServiceRegistryBuilder registryBuilder =
//                        new StandardServiceRegistryBuilder();
//                Map<String, String> dbSettings = new HashMap<>();
//                dbSettings.put(Environment.URL, connectionURL);
//                dbSettings.put(Environment.USER, userName);
//                dbSettings.put(Environment.PASS, password);
//                dbSettings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
//                dbSettings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
//                registryBuilder.applySettings(dbSettings);
//                standardServiceRegistry = registryBuilder.build();
//                MetadataSources sources = new MetadataSources(standardServiceRegistry).addAnnotatedClass(User.class);
//                sessionFactory = sources.buildMetadata().buildSessionFactory();
//            } catch (HibernateException e) {
//                System.err.println("SessionFactory creation failed");
//                if (standardServiceRegistry != null) {
//                    StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
//                }
//            }
//        }
//        return sessionFactory;
//    }
//
//
//    public static SessionFactory getSessionFactory() {
//        return getSessionFactory(connectionURL, userName, password);
//    }


    /*-----------------------------------------------------*/
    public static Connection getMySQLConnection() {

        return getMySQLConnection(hostName, dbName, userName, password);
    }

    private static Connection getMySQLConnection(String hostName, String dbName, String userName, String password) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connectionURL, userName,
                    password);
            if (!conn.isClosed()) {
                System.out.println("Соединение с БД установленно!");
            }
        } catch (SQLException e) {
            System.err.println("Не удалось загрузить класс драйвера");
        }
        return conn;
    }


}
