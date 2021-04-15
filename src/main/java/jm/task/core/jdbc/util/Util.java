package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static Connection getMySQLConnection() {

        String hostName = "localhost";
        String dbName = "mydbtest";
        String userName = "root";
        String password = "pak4795";

        return getMySQLConnection(hostName, dbName, userName, password);


    }

    public static Connection getMySQLConnection(String hostName, String dbName, String userName, String password) {
        Connection conn = null;

        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?autoReconnect=true&useSSL=false";
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
