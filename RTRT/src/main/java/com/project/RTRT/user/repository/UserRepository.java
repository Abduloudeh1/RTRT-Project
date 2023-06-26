package com.project.RTRT.user.repository;

import javax.transaction.Transactional;

import com.project.RTRT.user.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<AppUser, Integer> {

    boolean existsByEmail(String email);

    AppUser findByEmail(String email);

    public Optional<AppUser> findByFirstNameAndLastName(String firstname, String lastname);


    @Transactional
    void deleteByEmail(String email);

}