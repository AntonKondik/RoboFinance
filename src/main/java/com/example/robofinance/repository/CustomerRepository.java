package com.example.robofinance.repository;
import com.example.robofinance.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}
