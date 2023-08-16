package com.example.post.exception;

public class PackageDeliveryException extends RuntimeException {
    public PackageDeliveryException(String message) {
        super(message);
    }

    public PackageDeliveryException(String message, Throwable cause) {
        super(message, cause);
    }
}
