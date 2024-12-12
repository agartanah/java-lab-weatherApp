package org.example.javalabweatherapp.data.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WeatherCityRepository {
    private static final Connection connection;

    private static final Logger logger = LoggerFactory.getLogger(WeatherCityRepository.class);

    static  {
        try {
            connection = Database.getConnection();
        } catch (Exception e) {
            logger.error("Connection to database error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static String getCity() throws IOException {
        String query = Queries.getCity();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            return resultSet.getString("city_name");
        } catch (SQLException e) {
            logger.error("Receiving data from java-lab-weatherApp exception: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void insertWeatherCity(String city) throws IOException {
        String query = Queries.insertWeatherCity(city);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, city);

            statement.executeUpdate();

        } catch (SQLException e) {
            logger.error("Insert data to java-lab-weatherApp exception: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
