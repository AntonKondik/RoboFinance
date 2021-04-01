package com.example.robofinance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CustomerDto {

    @EqualsAndHashCode.Include
    private Long id;
    private AddressDto registredAddress;
    private AddressDto actualAddress;
    private String firstName;
    private String lastName;
    private String middleName;
    private String sex;
}
