package org.example.javalabweatherapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.javalabweatherapp.data.api.SearchRequest;
import org.example.javalabweatherapp.data.api.WeatherFewDaysRequest;
import org.example.javalabweatherapp.data.api.WeatherRequest;
import org.example.javalabweatherapp.data.database.WeatherCityRepository;
import org.example.javalabweatherapp.data.modal.*;
import org.example.javalabweatherapp.exceptions.ErrorReadingProperties;
import org.example.javalabweatherapp.exceptions.IncorrectTypeRequest;
import org.example.javalabweatherapp.exceptions.NullRequest;
import org.example.javalabweatherapp.exceptions.ReceivingResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class WeatherController implements Initializable {
    @FXML
    private TextField cityInput;
    @FXML
    private Label cityLabel;
    @FXML
    private Label temperatureLabel;
    @FXML
    private Label feelsLikeLabel;
    @FXML
    private Label minTempLabel;
    @FXML
    private Label maxTempLabel;
    @FXML
    private Label humidityLabel;
    @FXML
    private Label pressureLabel;
    @FXML
    private Label windLabel;
    @FXML
    private Label cloudinessLabel;
    @FXML
    private Label precipitationLabel;
    @FXML
    private ImageView weatherIcon;
    @FXML
    private VBox fewDaysContainer;
    private final List<HBox> listFewDays = new LinkedList<>();
    @FXML
    private ListView<String> suggestionList = new ListView<>();

    private static final Properties properties = new Properties();

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String path = "src/main/resources/org/example/javalabweatherapp/api.properties";

        try (FileInputStream input = new FileInputStream(path)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Reading properties error: {}", e.getMessage());
            throw new ErrorReadingProperties(e.getMessage());
        }

        try {
            String city = WeatherCityRepository.getCity();

            if (Objects.equals(city, "")) {
                return;
            }

            fetchWeatherData(city);
            fetchWeatherFewDays(city);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        cityInput.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            String city = cityInput.getText().toLowerCase();

            Task<SearchCity> searchCityTask = new Task<>() {
                @Override
                protected SearchCity call() throws IncorrectTypeRequest, NullRequest, ReceivingResponseException {
                    return WeatherApi.getSearchCity(new SearchRequest(
                            city,
                            "5",  // Максимальное количество результатов
                            properties.getProperty("apikey")
                    ));
                }
            };

            searchCityTask.setOnSucceeded(event -> {
                try {
                    SearchCity searchCity = searchCityTask.get();

                    if (!city.isEmpty()) {
                        ObservableList<String> citiesNames = FXCollections.observableArrayList(
                                searchCity.getList().stream()
                                        .map(City::getName)
                                        .collect(Collectors.toList())
                        );

                        if (!citiesNames.isEmpty()) {
                            suggestionList.setItems(citiesNames);
                            suggestionList.setVisible(true);
                            suggestionList.toFront();
                        } else {
                            suggestionList.setVisible(false);
                            suggestionList.toBack();
                        }
                    } else {
                        suggestionList.setVisible(false);
                        suggestionList.toBack();
                    }
                } catch (Exception e) {
                    // Обработка исключений, выброшенных в потоке Task
                    if (e.getCause() instanceof IncorrectTypeRequest) {
                        temperatureLabel.setText(e.getCause().getMessage());
                    } else if (e.getCause() instanceof NullRequest) {
                        temperatureLabel.setText(e.getCause().getMessage());
                    } else if (e.getCause() instanceof ReceivingResponseException) {
                        temperatureLabel.setText(e.getCause().getMessage());
                    } else {
                        logger.error(e.getCause().getMessage());
                        temperatureLabel.setText("Ошибка выполнения потока: " + e.getMessage());
                    }
                }
            });

            searchCityTask.setOnFailed(event -> {
                Throwable exception = searchCityTask.getException();
                if (exception != null) {
                    if (exception instanceof IncorrectTypeRequest) {
                        temperatureLabel.setText(exception.getMessage());
                    } else if (exception instanceof NullRequest) {
                        temperatureLabel.setText(exception.getMessage());
                    } else if (exception instanceof ReceivingResponseException) {
                        temperatureLabel.setText(exception.getMessage());
                    } else {
                        temperatureLabel.setText("Ошибка выполнения потока: " + exception.getMessage());
                    }
                }
            });

            try {
                new Thread(searchCityTask).start();
            } catch (Exception e) {
                temperatureLabel.setText("Ошибка выполнения потока: " + e.getMessage());
            }
        });

        cityInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!suggestionList.isFocused()) {
                    suggestionList.setVisible(false);
                }
            }
        });

        suggestionList.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                suggestionList.setVisible(false);
            }
        });

        suggestionList.setOnMouseClicked(event -> {
            String selected = suggestionList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                cityInput.setText(selected);
                suggestionList.setVisible(false);
                suggestionList.toBack();
            }
        });
    }

    @FXML
    private void handleGetWeather() {
        String city = cityInput.getText();
        if (city == null || city.trim().isEmpty()) {
            temperatureLabel.setText("Введите название города!");
            return;
        }

        try {
            fetchWeatherData(city);
        } catch (IncorrectTypeRequest e) {
            temperatureLabel.setText(e.getMessage());
            return;
        } catch (NullRequest e) {
            temperatureLabel.setText(e.getMessage());
            return;
        } catch (ReceivingResponseException e) {
            temperatureLabel.setText(e.getMessage());
            return;
        }

        try {
            fetchWeatherFewDays(city);
        } catch (IOException e) {
            temperatureLabel.setText("Ошибка получения данных");
        }
    }

    private void fetchWeatherData(String city) {
        WeatherCity weatherCity = WeatherApi
                .getWeather(new WeatherRequest(
                        city,
                        properties.getProperty("apikey"),
                        properties.getProperty("units"),
                        properties.getProperty("lang")
                ));

        weatherIcon.setImage(new Image(weatherCity.getWeather().getFirst().getIcon()));
        cityLabel.setText(city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase());
        precipitationLabel.setText(weatherCity.getWeather().getFirst().getDescription());
        temperatureLabel.setText(Math.round(weatherCity.getMain().getTemp()) + " °C");
        feelsLikeLabel.setText("Ощущается как " + Math.round(weatherCity.getMain().getFeelsLike()) + " °C");
        minTempLabel.setText(Math.round(weatherCity.getMain().getTempMin()) + " °C / " + Math.round(weatherCity.getMain().getTempMax()) + " °C");
        humidityLabel.setText("Влажность: " + Math.round(weatherCity.getMain().getHumidity()) + " %");
        pressureLabel.setText("Давление: " + Math.round(weatherCity.getMain().getPressure()) + " гПа");
        windLabel.setText("Ветер: " + Math.round(weatherCity.getWind().getSpeed()) + " м/с, " + weatherCity.getWind().getDirection());
        cloudinessLabel.setText("Облачность: " + Math.round(weatherCity.getCloud().getPercent()) + " %");
    }

    public void fetchWeatherFewDays(String city) throws IOException {
        fewDaysContainer.getChildren().removeAll(listFewDays);
        listFewDays.clear();

        WeatherFewDays weatherFewDays = WeatherApi
                .getWeatherFewDays(new WeatherFewDaysRequest(
                        city,
                        properties.getProperty("apikey"),
                        properties.getProperty("units"),
                        properties.getProperty("lang")
                ));

        List<WeatherDay> weatherDays =  weatherFewDays.getWeatherDays();

        System.out.println("Weather days");
        System.out.println(weatherDays);

        for (int index = 0; index < weatherDays.size() || index < 4; ++index) {
            HBox dayContainer = new HBox();
            WeatherDay weatherDay = weatherDays.get(index);

            ImageView iconForDay = new ImageView(new Image(weatherDay.getIcon()));
            iconForDay.setFitWidth(50);
            iconForDay.setFitHeight(50);

            Label labelDay = new Label(weatherDay.getDay().toString());
            labelDay.setStyle("-fx-text-fill: white;");

            long avgRound = Math.round(weatherDay.getAvgTemp());
            Label avgTemp = new Label(Long.toString(avgRound));
            avgTemp.setStyle("-fx-text-fill: white;");

            long minTempRound = Math.round(weatherDay.getMinTemp());
            Label minTemp = new Label(Long.toString(minTempRound));
            minTemp.setStyle("-fx-text-fill: white;");

            long maxTempRound = Math.round(weatherDay.getMaxTemp());
            Label maxTemp = new Label(Long.toString(maxTempRound));
            maxTemp.setStyle("-fx-text-fill: white;");

            dayContainer.setSpacing(20);
            dayContainer.setAlignment(Pos.CENTER_LEFT);
            dayContainer.getChildren().addAll(iconForDay, labelDay, avgTemp, minTemp, maxTemp);

            fewDaysContainer.getChildren().add(dayContainer);
            listFewDays.add(dayContainer);
        }

        WeatherCityRepository.insertWeatherCity(city);
    }
}