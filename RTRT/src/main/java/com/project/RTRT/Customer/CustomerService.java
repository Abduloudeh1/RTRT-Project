package com.project.RTRT.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getAllCustomer() {
        return this.customerRepository.findAll();
    }

    public Optional<Customer> getMyInfo(Long id) {
        return this.customerRepository.findById(id);
    }

    public Optional<Customer> getCustomerByName(String firstname, String lastname) {
        return this.customerRepository.findByFirstNameAndLastName(firstname, lastname);
    }

    public ResponseEntity<Customer> addCustomer(Customer customer) {
        /*
        if (customer.getEmail() == null && customer.getPassword() == null) {
            customer.setEmail("");
            customer.setPassword("");
        }
        if (customer.getEmail().isEmpty()){
            customer.setRegistered(false);
        }else{
            customer.setRegistered(true);
        }*/

        return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.CREATED);
    }


    public ResponseEntity<Customer> deleteCustomer(Long id) {
        if (this.customerRepository.findById(id).isPresent()) {
            this.customerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Customer> updateCustomer(Long id, Customer customer) {
        if (this.customerRepository.findById(id).isPresent()) {
            customer.setCustomerId(id);
            customer.setRegistered(true);
            return new ResponseEntity<>(this.customerRepository.save(customer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
