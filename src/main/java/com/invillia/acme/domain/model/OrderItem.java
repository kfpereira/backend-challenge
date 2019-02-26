package com.invillia.acme.domain.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ORDER_ITEM")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class OrderItem {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ITEM_ID")
    @SequenceGenerator(
            name = "SEQ_ITEM_ID",
            allocationSize = 1,
            sequenceName = "SEQ_ITEM_ID"
    )
    private Long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;

    @Column(name = "QUANTITY")
    private int quantity;

}
