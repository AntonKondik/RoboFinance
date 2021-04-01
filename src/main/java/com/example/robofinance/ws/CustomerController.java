package com.example.robofinance.ws;

import com.example.robofinance.dto.AddressDto;
import com.example.robofinance.dto.CustomerDto;
import com.example.robofinance.service.CustomerService;
import javassist.NotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Slf4j
@RestController
@RequestMapping(value = "/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping(method = POST, value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDto> createCustomer(@NonNull @RequestBody CustomerDto customer) {
        CustomerDto res = customerService.create(customer);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @RequestMapping(method = PUT, value = "/updateActualAddress", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDto> updateActualAddress(@NonNull @RequestBody AddressDto actualAddressDto,
                                                           @RequestParam("id") Long id) throws NotFoundException {
        CustomerDto res = customerService.updateActualAddress(actualAddressDto, id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "/get/{firstName}/{lastName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerDto>> searchCustomer(@PathVariable("firstName") String firstName,
                                                      @PathVariable("lastName") String lastName) {
        List<CustomerDto> res = customerService.searchCustomerByFirstNameAndLastName(firstName, lastName);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
