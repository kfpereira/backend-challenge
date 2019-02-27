package com.invillia.acme.viewer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderVM {

    @ApiModelProperty(required = true)
    private AddressVM address;

    @ApiModelProperty(required = true)
    private List<ItemsVM> items;
}
