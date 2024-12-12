package org.example.javalabweatherapp.data.api;

import org.example.javalabweatherapp.exceptions.ErrorRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SearchRequest extends Request {
    String endpoint = "direct";
    String city, limit, apiKey;

    private static final Logger logger = LoggerFactory.getLogger(SearchRequest.class);

    public SearchRequest(String city, String limit, String apiKey) {
        this.city = city;
        this.apiKey = apiKey;
        this.limit = limit;

        baseUrl = "https://api.openweathermap.org/geo/1.0/";
    }

    @Override
    public String getResponse() throws ErrorRequest {
        try {
            HttpRequest request = builder
                    .uri(new URI(baseUrl + endpoint +
                            "?q=" + city +
                            "&appid=" + apiKey +
                            "&limit=" + limit))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assert response.statusCode() != 200;

            logger.info("Ответ SEARCH: {}", request.uri());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Request error: {}", e.getMessage());
            throw new ErrorRequest(e.getMessage());
        }
    }
}

