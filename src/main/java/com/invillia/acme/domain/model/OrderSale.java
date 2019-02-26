package com.invillia.acme.domain.model;

import com.invillia.acme.domain.types.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_SALE_ID")
    private List<OrderItem> itens;

}
