package com.example.robofinance.service.impl;

import com.example.robofinance.utils.CustomerConverter;
import com.example.robofinance.dto.AddressDto;
import com.example.robofinance.dto.CustomerDto;
import com.example.robofinance.entity.Address;
import com.example.robofinance.entity.Customer;
import com.example.robofinance.repository.CustomerRepository;
import com.example.robofinance.service.CustomerService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerDto create(CustomerDto customerDto) {
        Customer customer = customerRepository.save(CustomerConverter.convertToCustomer(customerDto));
        log.debug("Created a new customer: {} ", customer);
        return CustomerConverter.convertToCustomerDto(customer);
    }

    @Override
    public CustomerDto updateActualAddress(AddressDto actualAddressDto, Long CustomerId) throws NotFoundException {
        Optional<Customer> oCustomer = customerRepository.findById(CustomerId);
        Customer customer =
                oCustomer.orElseThrow(() -> new NotFoundException("Did not find customer with id " + CustomerId));
        Address actualAddress = customer.getActualAddress();
        actualAddress.setCountry(actualAddressDto.getCountry());
        actualAddress.setRegion(actualAddressDto.getRegion());
        actualAddress.setCity(actualAddressDto.getCity());
        actualAddress.setStreet(actualAddressDto.getStreet());
        actualAddress.setHouse(actualAddressDto.getHouse());
        actualAddress.setFlat(actualAddressDto.getFlat());
        actualAddress.setModified(new Date());
        customer.setActualAddress(actualAddress);
        Customer res = customerRepository.save(customer);
        log.debug("The customer with id = {} changed the actual address to: {}", CustomerId, actualAddress);
        return CustomerConverter.convertToCustomerDto(res);
    }

    @Override
    public List<CustomerDto> searchCustomerByFirstNameAndLastName(String firstName, String lastName) {
        List<Customer> customers = customerRepository.findByFirstNameAndLastName(firstName, lastName);
        if (!customers.isEmpty()) {
            log.debug("Client found {}", customers);
        } else {
            log.warn("A client with the first name {} and the last name {} not found", firstName, lastName);
        }
        return customers.stream().map(CustomerConverter::convertToCustomerDto).collect(Collectors.toList());
    }
}
