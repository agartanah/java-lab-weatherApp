package org.example.javalabweatherapp.exceptions;

public class NullRequest extends RuntimeException {
    public NullRequest(String message) {
        super(message);
    }
}
