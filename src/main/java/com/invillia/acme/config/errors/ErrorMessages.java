package com.invillia.acme.config.errors;

import lombok.Getter;

@Getter
public enum ErrorMessages {

    RECORD_FOUND("This record exists in database");

    private String message;

    ErrorMessages(String message) {
        this.message = message;
    }

}
