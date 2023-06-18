package com.project.RTRT.configuration;



import com.project.RTRT.user.model.AppUser;
import com.project.RTRT.user.model.AppUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.project.RTRT.user.service.UserService;
@Component
public class DatabaseLoader implements CommandLineRunner {


    @Autowired
    UserService userService;
    @Override
    public void run(String... args) throws Exception {

        AppUser admin = new AppUser();
        admin.setPassword("admin");
        admin.setEmail("admin@gmail.com");
        admin.setFirstName("Abdul");
        admin.setLastName("Oudeh");
        admin.setRegistered(true);
        admin.setAppUserRoles(AppUserRole.ROLE_ADMIN);
        userService.signup(admin);

        AppUser client = new AppUser();
        client.setPassword("client");
        client.setEmail("client@email.com");
        client.setFirstName("max");
        client.setLastName("mustermann");
        client.setRegistered(true);
        client.setAppUserRoles(AppUserRole.ROLE_CLIENT);
        userService.signup(client);

    }
}