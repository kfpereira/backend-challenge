package com.invillia.acme.config;

import com.invillia.acme.config.inits.InitUfBrazil;
import com.invillia.acme.domain.repositories.UfRepository;
import com.invillia.acme.domain.services.OrderRefundService;
import com.invillia.acme.domain.services.OrderService;
import com.invillia.acme.domain.services.PaymentRefundService;
import com.invillia.acme.domain.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final InitUfBrazil initUfBrazil;
    private final UfRepository ufRepository;
    private final EnvironmentReader environmentReader;
    private final PaymentService paymentService;
    private final OrderService orderService;
    private OrderRefundService orderRefundService;
    private PaymentRefundService paymentRefundService;

    @Autowired
    public DataLoader(InitUfBrazil initUfBrazil,
                      UfRepository ufRepository,
                      EnvironmentReader environmentReader,
                      PaymentService paymentService,
                      OrderService orderService,
                      OrderRefundService orderRefundService,
                      PaymentRefundService paymentRefundService) {
        this.initUfBrazil = initUfBrazil;
        this.ufRepository = ufRepository;
        this.environmentReader = environmentReader;
        this.paymentService = paymentService;
        this.orderService = orderService;
        this.orderRefundService = orderRefundService;
        this.paymentRefundService = paymentRefundService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        paymentService.subscribe(orderService);
        orderRefundService.subscribe(paymentRefundService);

        if (!environmentReader.isAmbienteDeTeste() && ufRepository.findAll().isEmpty()) initUfBrazil.init();
    }
}
