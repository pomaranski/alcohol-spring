package com.ppbkaf.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MailServiceException extends RuntimeException {
    public MailServiceException() {
        super();
    }
}
