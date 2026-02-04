package edu.aitu.oop3.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseConnection {

    private static DatabaseConnection instance;

    private static final String HOST = "aws-1-ap-south-1.pooler.supabase.com";
    private static final int PORT = 5432;
    private static final String DB = "postgres";

    private static final String USER = "postgres.wwxfjgpoiwamoawhdiia";
    private static final String PASSWORD = "queen.007Aish";

    private DatabaseConnection() {

    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection open() throws SQLException {
        String url = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DB
                + "?loginTimeout=10&connectTimeout=10&socketTimeout=30";

        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);
        props.setProperty("sslmode", "require");

        System.out.println("Connecting as USER=" + USER + " via Session Pooler");
        return DriverManager.getConnection(url, props);
    }


    public static Connection getConnection() throws SQLException {
        return getInstance().open();
    }
}