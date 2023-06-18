package com.project.RTRT.configuration;

import com.project.RTRT.Customer.Customer;
import com.project.RTRT.Customer.CustomerRepository;
import com.project.RTRT.tables.Tisch;
import com.project.RTRT.tables.TischRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
//@RequiredArgsConstructor
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    TischRepository tischRepository;

    @Override
    public void run(String... args) throws Exception {

        Customer customer=new Customer();
        customer.setCustomerId(1L);
        customer.setFirstName("Abdul");
        customer.setLastName("Oudeh");
        customer.setEmail("test@gmail.com");
        customer.setPassword("test");
        customer.setBirthDay(LocalDate.of(1998, 8, 20));
        customer.setRegistered(true);
        customerRepository.save(customer);

        Tisch tisch = new Tisch();
        tisch.setTableId(1);
        tischRepository.save(tisch);


    }
}