package com.example.post.exception;

public class PackageStatusException extends RuntimeException {
    public PackageStatusException(String message) {
        super(message);
    }

    public PackageStatusException(String message, Throwable cause) {
        super(message, cause);
    }
}
