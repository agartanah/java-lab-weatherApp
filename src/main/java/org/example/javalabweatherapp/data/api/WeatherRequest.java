package org.example.javalabweatherapp.data.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.javalabweatherapp.exceptions.ErrorRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@EqualsAndHashCode(callSuper = true)
@Data
public class WeatherRequest extends Request {
    String endpoint = "weather";
    String city, apiKey, units, lang;

    private static final Logger logger = LoggerFactory.getLogger(WeatherRequest.class);

    public WeatherRequest(String city, String apiKey, String units, String lang) {
        this.city = city;
        this.apiKey = apiKey;
        this.units = units;
        this.lang = lang;
    }

    @Override
    public String getResponse() throws ErrorRequest {
        try {
            HttpRequest request = builder
                    .uri(new URI(baseUrl + endpoint +
                            "?q=" + city +
                            "&appid=" + apiKey +
                            "&units=" + units +
                            "&lang=" + lang))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assert response.statusCode() != 200;

            logger.info("Ответ SEARCH: {}", request.uri());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Request error: {}", e.getMessage());
            throw new ErrorRequest("API data retrieval error");
        }
    }
}
