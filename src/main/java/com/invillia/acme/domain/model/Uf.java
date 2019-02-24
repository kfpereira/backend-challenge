package com.invillia.acme.domain.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "UF",
        uniqueConstraints = @UniqueConstraint(name = "UC_UF_INITIAL", columnNames = {"INITIAL"}))
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class Uf {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_UF_ID")
    @SequenceGenerator(
            name = "SEQ_UF_ID",
            allocationSize = 1,
            sequenceName = "SEQ_UF_ID"
    )
    private Long id;

    @Column(name = "INITIAL")
    private String initial;

    @Column(name = "NAME")
    private String name;

}
