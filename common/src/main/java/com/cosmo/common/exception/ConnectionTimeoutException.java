package com.cosmo.common.exception;

public class ConnectionTimeoutException extends RuntimeException {

    public ConnectionTimeoutException(String message) {
        super(message);
    }
}

