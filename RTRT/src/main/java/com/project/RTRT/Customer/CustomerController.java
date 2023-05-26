package com.project.RTRT.Customer;

import com.project.RTRT.*;
import com.project.RTRT.reservation.Reservation;
import com.project.RTRT.reservation.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "customer")
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ReservationRepository reservationRepository;

    @GetMapping("findall")
    // Get all customers (this method to test the functionality of the add delete and update methods)
    public List<Customer> getAllCustomer() {
        return this.customerRepository.findAll();
    }

    @GetMapping("findMyInfo/{id}")
    //the user will get his own information
    public Optional<Customer> getMyInfo(@PathVariable Long id) {
        return this.customerRepository.findById(id);
    }

    @PostMapping("addCustomer")
    // Add a new customer (registration)
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerRepository.save(customer), HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    // Delete a customer
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
        if (this.customerRepository.findById(id).isPresent()) {
            this.customerRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        if (this.customerRepository.findById(id).isPresent()) {
            customer.setCustomerId(id);
            return new ResponseEntity<>(this.customerRepository.save(customer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*@PostMapping("addReservation")
    // Add a new reservation
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        return new ResponseEntity<>(reservationRepository.save(reservation), HttpStatus.CREATED);
    }
*/



}
