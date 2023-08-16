package com.example.post.exception;

public class PostOfficeNotExistException extends RuntimeException{
    public PostOfficeNotExistException(String message) {
        super(message);
    }

    public PostOfficeNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
