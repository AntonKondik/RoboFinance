package com.example.robofinance.entity;

import com.example.robofinance.enums.Sex;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name="CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CustomerIdGenerator")
    @SequenceGenerator(name = "CustomerIdGenerator", sequenceName = "customer_id_seq", initialValue = 1, allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "REGISTRED_ADDRESS_ID", nullable = false)
    private Address registredAddress;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "ACTUAL_ADDRESS_ID", nullable = false)
    private Address actualAddress;

    @Column(name = "FIRST_NAME", nullable = true)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = true)
    private String lastName;

    @Column(name = "MIDDLE_NAME", nullable = true)
    private String middleName;

    @Column(name = "SEX", nullable = false)
    @Enumerated(EnumType.STRING)
    private Sex sex;
}
