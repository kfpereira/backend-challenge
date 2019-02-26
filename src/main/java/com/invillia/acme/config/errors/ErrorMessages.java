package com.invillia.acme.config.errors;

import lombok.Getter;

@Getter
public enum ErrorMessages {

    RECORD_FOUND("This record exists in database"),
    RECORD_NOT_FOUND("This record doesn't exist in database"),
    EMPTY_DATA("This data can't be null or empty"),
    INVALID_CNPJ_CPF("CNPJ/CPF invalid"),
    CREDIT_CARD_INVALID("Invalid Credit Card");

    private String message;

    ErrorMessages(String message) {
        this.message = message;
    }

}
