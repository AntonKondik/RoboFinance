package com.example.robofinance.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name="ADDRESS")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AddressIdGenerator")
    @SequenceGenerator(name = "AddressIdGenerator", sequenceName = "address_id_seq", initialValue = 1, allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "COUNTRY", nullable = true)
    private String country;

    @Column(name = "REGION", nullable = true)
    private String region;

    @Column(name = "CITY", nullable = true)
    private String city;

    @Column(name = "STREET", nullable = true)
    private String street;

    @Column(name = "HOUSE", nullable = true)
    private String house;

    @Column(name = "FLAT", nullable = true)
    private String flat;

    @Column(name = "CREATED", nullable = true)
    private Date created;

    @Column(name = "MODIFIED", nullable = true)
    private Date modified;
}
