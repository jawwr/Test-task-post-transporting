package com.example.post.exception;

public class PostOfficeAlreadyExistException extends RuntimeException {
    public PostOfficeAlreadyExistException(String message) {
        super(message);
    }

    public PostOfficeAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
