package com.solution.developerManager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DeveloperNotFoundException extends RuntimeException {
    public DeveloperNotFoundException(String msg) {
        super(msg);
    }
}

