package com.invillia.acme.viewer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class AddressVM {

    private String streetName;
    private Integer numberHome;
    private String addressAddOn;
    private String cep;
    private CityVM city;

}
