package com.invillia.acme.domain.model;

import com.invillia.acme.domain.types.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PAYMENT")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class Payment {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_PAY_ID")
    @SequenceGenerator(
            name = "SEQ_PAY_ID",
            allocationSize = 1,
            sequenceName = "SEQ_PAY_ID"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private OrderSale orderSale;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "CREDIT_CARD_ID")
    private CreditCard creditCard;

    @Temporal(TemporalType.DATE)
    @Column(name = "PAYMENT_DATE")
    private Date paymentDate;

}
