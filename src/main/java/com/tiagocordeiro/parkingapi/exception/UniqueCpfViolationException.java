package com.tiagocordeiro.parkingapi.exception;

public class UniqueCpfViolationException extends RuntimeException {
    public UniqueCpfViolationException(String s) {
        super(s);
    }
}
