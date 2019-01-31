package com.ppbkaf.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GetException extends DatabaseException {
    public GetException(String message, Throwable cause) {
        super(message, cause);
    }
}
