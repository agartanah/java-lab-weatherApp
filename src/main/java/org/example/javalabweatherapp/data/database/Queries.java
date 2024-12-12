package org.example.javalabweatherapp.data.database;

import org.example.javalabweatherapp.data.modal.WeatherCity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Queries {
    public static String getCity() throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/main/resources/org/example/javalabweatherapp/queries/get-city.sql")));
    }

    public static String insertWeatherCity(String city) throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/main/resources/org/example/javalabweatherapp/queries/insert-weather.sql")));
    }
}
