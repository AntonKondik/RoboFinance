package com.example.robofinance.utils;

import com.example.robofinance.dto.AddressDto;
import com.example.robofinance.dto.CustomerDto;
import com.example.robofinance.entity.Address;
import com.example.robofinance.entity.Customer;
import com.example.robofinance.enums.Sex;

import java.util.Date;

public class CustomerConverter {

    public static Customer convertToCustomer(CustomerDto customerDto){
        Date date = new Date();
        return Customer.builder()
                .registredAddress(convertToAddress(customerDto.getRegistredAddress(), date))
                .actualAddress(convertToAddress(customerDto.getActualAddress(), date))
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .middleName(customerDto.getMiddleName())
                .sex(Sex.valueOf(customerDto.getSex()))
                .build();
    }

    public static CustomerDto convertToCustomerDto(Customer customer){
        return CustomerDto.builder().id(customer.getId())
                .registredAddress(convertToAddressDto(customer.getRegistredAddress()))
                .actualAddress(convertToAddressDto(customer.getActualAddress()))
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .middleName(customer.getMiddleName())
                .sex(customer.getSex().name())
                .build();
    }

    public static Address convertToAddress(AddressDto addressDto, Date date){
        return Address.builder()
                .country(addressDto.getCountry())
                .region(addressDto.getRegion())
                .city(addressDto.getCity())
                .street(addressDto.getStreet())
                .house(addressDto.getHouse())
                .flat(addressDto.getFlat())
                .created(date)
                .modified(date)
                .build();
    }

    public static AddressDto convertToAddressDto(Address address){
        return AddressDto.builder().id(address.getId())
                .country(address.getCountry())
                .region(address.getRegion())
                .city(address.getCity())
                .street(address.getStreet())
                .house(address.getHouse())
                .flat(address.getFlat())
                .build();
    }
}
