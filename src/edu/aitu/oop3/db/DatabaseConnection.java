package edu.aitu.oop3.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {


    private static final String HOST = "aws-1-ap-southeast-2.pooler.supabase.com";
    private static final int PORT = 5432;
    private static final String DB = "postgres";

    private static final String USER = "postgres.inemvmgmvphsjkybiwmu";
    private static final String PASSWORD = "iORK1lHFMs5VRR4w";

    private static final String URL =
            "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DB;

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);
        props.setProperty("sslmode", "require");

        System.out.println("Connecting as USER=" + USER);
        return DriverManager.getConnection(URL, props);
    }
}
