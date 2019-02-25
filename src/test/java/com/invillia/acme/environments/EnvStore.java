package com.invillia.acme.environments;

import com.invillia.acme.config.exceptions.RecordFoundException;
import com.invillia.acme.domain.services.AddressService;
import com.invillia.acme.domain.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnvStore {

    @Autowired
    private EnvAddress envAddress;

    @Autowired
    private AddressService addressService;

    @Autowired
    private StoreService service;

    public void init() throws RecordFoundException {
        envAddress.init();

        service.save("TEST ONE",
                addressService.save("AV. LONDRINA",
                    "980",
                    "SL 01",
                    "zona 08",
                    "87050-390",
                    "maringa",
                    "pr")
        );
        service.save("TESTE TWO",
                addressService.save("AV. LONDRINA",
                        "980",
                        "SL 02",
                        "zona 08",
                        "87050-390",
                        "maringa",
                        "pr")
        );
    }
}
