package org.example.javalabweatherapp.data.database;

import lombok.Data;
import lombok.Getter;
import org.example.javalabweatherapp.exceptions.ErrorReadingProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static String url;
    private static String user;
    private static String password;
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    @Getter
    private static Connection connection;

    static {
        Properties properties = new Properties();

        String path = "src/main/resources/org/example/javalabweatherapp/db.properties";

        try (FileInputStream input = new FileInputStream(path)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Reading properties error: {}", e.getMessage());
            throw new ErrorReadingProperties(e.getMessage());
        }

        url = properties.getProperty("url");
        user = properties.getProperty("user");
        password = properties.getProperty("password");

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Database error: {}", e.getMessage());
            throw new RuntimeException("Failed to establish connection to the database", e);
        }
    }
}
