package com.invillia.acme.viewer;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreditCardVM {

    private String number;
    private String name;
    private String cnpjCpf;
    private Date validateDate;
    private String securityCode;

}
