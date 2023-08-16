package com.example.post.exception;

public class PackageNotExistException extends RuntimeException {
    public PackageNotExistException(String message) {
        super(message);
    }

    public PackageNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
