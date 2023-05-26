package com.project.RTRT.admin;
import com.project.RTRT.Customer.*;

import com.project.RTRT.tables.Tisch;
import com.project.RTRT.tables.TischRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "admin")
public class AdminController {
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    TischRepository tischRepository;

    @GetMapping("findallCustomer")
    public List<Customer> getCustomer(){
        return customerRepository.findAll();
    }
    @PostMapping("addtisch")
    public ResponseEntity<Tisch> addTable(@RequestBody Tisch table){
        // add a new table
        return new ResponseEntity<>(tischRepository.save(table), HttpStatus.CREATED);
    }



}
