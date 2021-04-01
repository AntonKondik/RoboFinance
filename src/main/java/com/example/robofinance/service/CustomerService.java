package com.example.robofinance.service;

import com.example.robofinance.dto.AddressDto;
import com.example.robofinance.dto.CustomerDto;
import javassist.NotFoundException;

import java.util.List;

public interface CustomerService {

    CustomerDto create(CustomerDto customerDto);
    CustomerDto updateActualAddress(AddressDto actualAddressDto, Long idCustomer) throws NotFoundException;
    List<CustomerDto> searchCustomerByFirstNameAndLastName(String firstName, String lastName);

}
