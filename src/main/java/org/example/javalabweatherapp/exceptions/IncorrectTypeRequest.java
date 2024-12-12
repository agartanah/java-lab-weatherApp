package org.example.javalabweatherapp.exceptions;

public class IncorrectTypeRequest extends RuntimeException {
    public IncorrectTypeRequest(String message) {
        super(message);
    }
}
