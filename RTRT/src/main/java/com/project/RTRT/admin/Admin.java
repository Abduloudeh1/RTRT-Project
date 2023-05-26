package com.project.RTRT.admin;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(updatable = false, unique = true)
    private Long adminId;

    @Column(nullable = false,updatable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;


    public Admin(Long adminId,String email, String password) {
        this.adminId = adminId;
        this.email = email;
        this.password = password;
    }
}
