package com.invillia.acme.viewer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreVM {

    @ApiModelProperty(required=true)
    private String name;

    @ApiModelProperty(required=true)
    private AddressVM address;

}
