package com.invillia.acme.environments;

import com.invillia.acme.config.exceptions.RecordFoundException;
import com.invillia.acme.config.inits.InitUfBrazil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnvUf {

    @Autowired
    private InitUfBrazil initUfBrazil;

    public void init() throws RecordFoundException {
        initUfBrazil.init();
    }
}
