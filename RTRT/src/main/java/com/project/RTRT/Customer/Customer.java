package com.project.RTRT.Customer;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthOfDate;

    public Customer() {
    }

    public Customer(Integer customerId, String firstName, String lastName,
                    String email, String password, LocalDate birthOfDate) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthOfDate = birthOfDate;
    }
}
