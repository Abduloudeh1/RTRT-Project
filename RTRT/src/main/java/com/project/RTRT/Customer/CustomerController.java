package com.project.RTRT.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "customer")
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("findall")
    // Get all customers
    public List<Customer> getCustomer() {
        return this.customerRepository.findAll();
    }

    @PostMapping("add")
    // Add a new customer
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    // Delete a customer
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Integer id) {
        if (this.customerRepository.findById(id).isPresent()) {
            this.customerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        if (this.customerRepository.findById(id).isPresent()) {
            customer.setCustomerId(id);
            return new ResponseEntity<>(this.customerRepository.save(customer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
