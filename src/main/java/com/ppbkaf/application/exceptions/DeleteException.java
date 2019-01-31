package com.ppbkaf.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DeleteException extends DatabaseException {
    public DeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
