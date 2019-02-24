package com.invillia.acme.environments;

import com.invillia.acme.config.exceptions.RecordFoundException;
import com.invillia.acme.domain.model.City;
import com.invillia.acme.domain.services.AddressService;
import com.invillia.acme.domain.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnvAddress {

    @Autowired
    private EnvCity envCity;

    @Autowired
    private CityService cityService;

    @Autowired
    private AddressService service;

    public void init() throws RecordFoundException {
        envCity.init();

        City maringaPr = cityService.find("maringa", "pr");

        service.save("AV. BRASIL", "2500", null, "CENTRO", "87500-000", maringaPr);
        service.save("AV. LONDRINA", "980", "SL 01", "ONA 08", "87050-390", maringaPr);
    }
}
