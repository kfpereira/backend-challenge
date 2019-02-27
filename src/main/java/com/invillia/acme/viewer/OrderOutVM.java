package com.invillia.acme.viewer;

import com.invillia.acme.domain.types.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderOutVM {

    private Long id;
    private Date confirmationDate;
    private BigDecimal value;
    private Status status;
    private AddressVM address;
    private List<ItemsOutVM> items;

}
