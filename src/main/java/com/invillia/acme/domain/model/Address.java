package com.invillia.acme.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS",
        uniqueConstraints = @UniqueConstraint(name = "UC_ADDRESS", columnNames = {"STREET_NAME", "NUMBER", "ADD_ON", "CEP"}))
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class Address {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_AD_ID")
    @SequenceGenerator(
            name = "SEQ_AD_ID",
            allocationSize = 1,
            sequenceName = "SEQ_AD_ID"
    )
    private Long id;

    @Column(name = "STREET_NAME")
    private String streetName;

    @Column(name = "NUMBER")
    private String number;

    @Column(name = "ADD_ON")
    private String addOn;

    @Column(name = "NEIGHBORHOOD")
    private String neighborhood;

    @Column(name = "CEP")
    private String cep;

    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    private City city;
}
