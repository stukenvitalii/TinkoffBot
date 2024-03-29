package edu.java.exception;

public class ServerException extends RuntimeException {
    private final int statusCode;

    public ServerException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
