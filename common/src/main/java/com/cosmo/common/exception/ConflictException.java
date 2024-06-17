package com.cosmo.common.exception;

/**
 * Author: Sapana Rimal
 * Date: 4/29/2024
 */
public class ConflictException extends RuntimeException {

    public ConflictException() {
        super();
    }

    public ConflictException(final String message) {
        super(message);
    }

}