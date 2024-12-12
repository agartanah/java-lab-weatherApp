package org.example.javalabweatherapp.exceptions;

public class ErrorRequest extends RuntimeException {
    public ErrorRequest(String message) {
        super(message);
    }
}
