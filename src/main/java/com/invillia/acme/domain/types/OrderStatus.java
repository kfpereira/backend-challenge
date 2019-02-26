package com.invillia.acme.domain.types;

public enum OrderStatus {

    OPENED("OPENDED"),
    WAITING_PAYMENT("WAITING PAYMENT"),
    PAID("PAID");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }
}
