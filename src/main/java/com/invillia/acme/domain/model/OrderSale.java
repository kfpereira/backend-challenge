package com.invillia.acme.domain.model;

import com.invillia.acme.domain.types.Status;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ORDER_SALE")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class OrderSale {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ORDER_ID")
    @SequenceGenerator(
            name = "SEQ_ORDER_ID",
            allocationSize = 1,
            sequenceName = "SEQ_ORDER_ID"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

    @Temporal(TemporalType.DATE)
    @Column(name = "CONFIRMATION_DATE")
    private Date confirmationDate;

    @Setter
    @Column(name = "VALUE")
    private BigDecimal value;

    @Setter
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private Status status;

}
