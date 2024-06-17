package com.cosmo.common.exception;

/**
 * Author: Sapana Rimal
 * Date: 4/29/2024
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
