package com.invillia.acme.environments;

import com.invillia.acme.config.exceptions.RecordFoundException;
import com.invillia.acme.config.inits.InitUfBrazil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnvUf {

    @Autowired
    private InitUfBrazil initUfBrazil;

    private static final Logger log = LoggerFactory.getLogger(EnvUf.class);

    public void init() {
        try {
            initUfBrazil.init();
        } catch (RecordFoundException e) {
            log.error(e.getMessage());
        }
    }
}
