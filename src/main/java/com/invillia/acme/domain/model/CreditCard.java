package com.invillia.acme.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CREDIT_CARD",
        uniqueConstraints = @UniqueConstraint(name = "UC_CC", columnNames = {"NUMBER", "CNPJ_CPF", "SECURITY_CODE"}))
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class CreditCard {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CC_ID")
    @SequenceGenerator(
            name = "SEQ_CC_ID",
            allocationSize = 1,
            sequenceName = "SEQ_CC_ID"
    )
    private Long id;

    @Column(name = "NUMBER", length = 16)
    private String number;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CNPJ_CPF", length = 14)
    private String cnpjCpf;

    @Temporal(TemporalType.DATE)
    @Column(name = "VALIDATE_DATE")
    private Date validateDate;

    @Column(name = "SECURITY_CODE", length = 3)
    private String securityCode;

}
