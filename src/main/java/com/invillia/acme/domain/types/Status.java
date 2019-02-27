package com.invillia.acme.domain.types;

public enum Status {

    OPENED("OPENED"),
    CONCLUDED("CONCLUDED"),
    REFUNDED("REFUNDED");

    private String value;

    Status(String value) {
        this.value = value;
    }
}
