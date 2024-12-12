package org.example.javalabweatherapp.data.api;

import lombok.Data;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Data
public abstract class Request {
    protected HttpClient client = HttpClient.newHttpClient();
    protected HttpRequest.Builder builder = HttpRequest.newBuilder();

    protected String baseUrl = "https://api.openweathermap.org/data/2.5/";

    abstract public String getResponse();
}
