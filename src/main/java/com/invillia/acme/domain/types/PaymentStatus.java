package com.invillia.acme.domain.types;

public enum PaymentStatus {

    OPENED("OPENED"),
    CONCLUDED("CONCLUDED"),
    REFUNDED("REFUNDED");

    private String status;

    PaymentStatus(String status) {
        this.status = status;
    }
}
