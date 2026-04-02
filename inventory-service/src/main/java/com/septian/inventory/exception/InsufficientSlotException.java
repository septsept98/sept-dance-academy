package com.septian.inventory.exception;

public class InsufficientSlotException extends RuntimeException {
    public InsufficientSlotException(String message) {
        super(message);
    }
}
