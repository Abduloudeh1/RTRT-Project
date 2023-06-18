package com.project.RTRT.admin;
import com.project.RTRT.Customer.*;
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



}
