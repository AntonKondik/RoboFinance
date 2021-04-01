package com.example.robofinance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AddressDto {

    @EqualsAndHashCode.Include
    private Long id;
    private String country;
    private String region;
    private String city;
    private String street;
    private String house;
    private String flat;
}
