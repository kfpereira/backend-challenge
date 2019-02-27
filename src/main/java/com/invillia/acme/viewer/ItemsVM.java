package com.invillia.acme.viewer;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemsVM {

    private String description;
    private BigDecimal unitPrice;
    private int quantity;

}
