package org.example.javalabweatherapp;

import java.io.FileInputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.javalabweatherapp.data.api.WeatherFewDaysRequest;
import org.example.javalabweatherapp.data.api.WeatherRequest;
import org.example.javalabweatherapp.data.modal.WeatherCity;
import org.example.javalabweatherapp.data.modal.WeatherFewDays;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("weather.fxml")));
        primaryStage.setTitle("Прогноз погоды");
        primaryStage.setScene(new Scene(root, 450, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
//        String path = "src/main/resources/org/example/javalabweatherapp/api.properties";
//
//        try (FileInputStream input = new FileInputStream(path)) {
//            properties.load(input);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e.getMessage());
//        }
//
//        WeatherCity weatherCity = WeatherApi
//                .getWeather(new WeatherRequest(
//                    "Кемерово",
//                    properties.getProperty("apikey"),
//                    properties.getProperty("units"),
//                    properties.getProperty("lang")
//                ));
//
//        System.out.println(weatherCity);
//
//        WeatherFewDays weatherFewDays = WeatherApi
//                .getWeatherFewDays(new WeatherFewDaysRequest(
//                        "Кемерово",
//                        properties.getProperty("apikey"),
//                        properties.getProperty("units"),
//                        properties.getProperty("lang")
//                ));
//
//        System.out.println(weatherFewDays);
    }
}