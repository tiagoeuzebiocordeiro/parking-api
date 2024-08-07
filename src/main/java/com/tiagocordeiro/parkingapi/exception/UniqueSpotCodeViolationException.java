package com.tiagocordeiro.parkingapi.exception;

public class UniqueSpotCodeViolationException extends RuntimeException {
    public UniqueSpotCodeViolationException(String s) {
        super(s);
    }
}
