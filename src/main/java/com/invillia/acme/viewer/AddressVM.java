package com.invillia.acme.viewer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressVM {

    @ApiModelProperty(required=true)
    private String streetName;

    @ApiModelProperty(required=true)
    private String numberHome;

    private String addressAddOn;

    @ApiModelProperty(required=true)
    private String neighborhood;

    @ApiModelProperty(required=true)
    private String cep;

    @ApiModelProperty(required=true)
    private CityVM city;

}
