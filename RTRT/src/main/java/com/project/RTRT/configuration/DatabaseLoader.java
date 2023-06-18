package com.project.RTRT.configuration;

import com.project.RTRT.Customer.Customer;
import com.project.RTRT.Customer.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import  com.project.RTRT.security.service.UserService;
import  com.project.RTRT.security.model.*;
@Component
//@RequiredArgsConstructor
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserService userService;
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

        AppUser admin = new AppUser();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setEmail("admin@gmail.com");
        admin.setFirstName("Abdul");
        admin.setLastName("Oudeh");
        admin.setRegistered(true);
        admin.setAppUserRoles(new ArrayList<AppUserRole>(Arrays.asList(AppUserRole.ROLE_ADMIN)));

        userService.signup(admin);

        AppUser client = new AppUser();
        client.setUsername("client");
        client.setPassword("client");
        client.setEmail("client@email.com");
        client.setFirstName("max");
        client.setLastName("mustermann");
        client.setRegistered(true);
        client.setAppUserRoles(new ArrayList<AppUserRole>(Arrays.asList(AppUserRole.ROLE_CLIENT)));

        userService.signup(client);

    }
}