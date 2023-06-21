package com.project.RTRT.user.repository;

import javax.transaction.Transactional;

import com.project.RTRT.user.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<AppUser, Integer> {

    boolean existsByEmail(String email);

    AppUser findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);

}