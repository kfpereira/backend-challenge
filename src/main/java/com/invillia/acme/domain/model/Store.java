package com.invillia.acme.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "STORE",
        uniqueConstraints = @UniqueConstraint(name = "UC_STORE", columnNames = {"NAME", "ADDRESS_ID"}))
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class Store {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_STORE_ID")
    @SequenceGenerator(
            name = "SEQ_STORE_ID",
            allocationSize = 1,
            sequenceName = "SEQ_STORE_ID"
    )
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Setter
    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;
}
