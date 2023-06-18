package com.project.RTRT.Customer;
import com.project.RTRT.reservation.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "customer")
public class CustomerController {


    @Autowired
    CustomerService customerService;

    @GetMapping("findall")

    // Get all customers (this method to test the functionality of the add delete and update methods)
    public List<Customer> getAllCustomer() {
        return customerService.getAllCustomer();
    }


    @GetMapping("findMyInfo/{id}")
    //the user will get his own information
    public Optional<Customer> getMyInfo(@PathVariable Long id) {
        return customerService.getMyInfo(id);
    }

    @GetMapping("findByName")
    public Optional<Customer> getCustomerByName(@RequestParam String firstname,@RequestParam String lastname){
        return customerService.getCustomerByName(firstname,lastname);
    }


    @PostMapping("addCustomer")
    // Add a new customer (registration) and from admin without login
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {

        return customerService.addCustomer(customer);
    }

    @DeleteMapping("delete/{id}")
    // Delete a customer
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
        return customerService.deleteCustomer(id);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

}
