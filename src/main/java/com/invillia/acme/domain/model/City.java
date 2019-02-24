package com.invillia.acme.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "CITY",
        uniqueConstraints = @UniqueConstraint(name = "UC_CITY", columnNames = {"NAME", "UF_ID"}))
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class City {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CITY_ID")
    @SequenceGenerator(
            name = "SEQ_CITY_ID",
            allocationSize = 1,
            sequenceName = "SEQ_CITY_ID"
    )
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "UF_ID")
    private Uf uf;
}
