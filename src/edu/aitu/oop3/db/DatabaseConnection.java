package edu.aitu.oop3.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseConnection {

    private static volatile DatabaseConnection instance;

    private final String url;
    private final Properties props;

    private DatabaseConnection() {

        String host = env("DB_HOST", "aws-1-ap-south-1.pooler.supabase.com");
        String port = env("DB_PORT", "5432");
        String db   = env("DB_NAME", "postgres");

        String user = env("DB_USER", "");
        String pass = env("DB_PASSWORD", "");

        this.url = "jdbc:postgresql://" + host + ":" + port + "/" + db;

        this.props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pass);
        props.setProperty("sslmode", env("DB_SSLMODE", "require"));
    }

    private static String env(String key, String def) {
        String v = System.getenv(key);
        return (v == null || v.isBlank()) ? def : v;
    }


    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) instance = new DatabaseConnection();
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, props);
    }
}
