package org.example.javalabweatherapp.exceptions;

public class ErrorReadingProperties extends RuntimeException {
    public ErrorReadingProperties(String message) {
        super(message);
    }
}
