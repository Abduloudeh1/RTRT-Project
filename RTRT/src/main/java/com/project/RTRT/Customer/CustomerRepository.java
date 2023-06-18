package com.project.RTRT.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Optional<Customer> findByEmail(String email);
    public Optional<Customer> findByFirstNameAndLastName(String firstname,String lastname);

}
