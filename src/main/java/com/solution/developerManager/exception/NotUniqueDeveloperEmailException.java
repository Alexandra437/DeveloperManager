package com.solution.developerManager.exception;

public class NotUniqueDeveloperEmailException extends RuntimeException {
    public NotUniqueDeveloperEmailException(String msg) {
        super(msg);
    }
}
