package com.invillia.acme.viewer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentVM {

    private Long idOrder;
    private CreditCardVM creditCard;
}
