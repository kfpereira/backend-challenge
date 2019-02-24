package com.invillia.acme.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RecordFoundException extends Exception {

    public RecordFoundException(String message) {
        super(message);
    }

}
