package libman06.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAO06 implements AutoCloseable {
    private static Properties config;

    protected Connection con;

    public DAO06() {
        Properties p = getConfig();
        try {
            con = DriverManager.getConnection(p.getProperty("db.url"), p.getProperty("db.user"),
                    p.getProperty("db.password"));
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to MySQL: " + e.getMessage(), e);
        }
    }

    @Override
    public void close() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ignored) {
        }
    }

    private static synchronized Properties getConfig() {
        if (config == null) {
            try (InputStream in = DAO06.class.getResourceAsStream("/db.properties")) {
                if (in == null) {
                    throw new IllegalStateException(
                            "Missing db.properties: copy src/main/resources/db.properties.example to db.properties");
                }
                Properties p = new Properties();
                p.load(in);
                config = p;
            } catch (IOException e) {
                throw new IllegalStateException("Cannot read db.properties", e);
            }
        }
        return config;
    }
}
