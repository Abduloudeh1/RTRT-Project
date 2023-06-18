package com.project.RTRT.user.controller;

import javax.servlet.http.HttpServletRequest;

import com.project.RTRT.user.dto.UserDataDTO;
import com.project.RTRT.user.dto.UserResponseDTO;
import com.project.RTRT.user.model.AppUser;
import com.project.RTRT.user.model.AppUserRole;
import com.project.RTRT.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @PostMapping("/signin")
    public String login(@RequestParam String email, @RequestParam String password) {
        return userService.signin(email, password);
    }

    @PostMapping("/signup")
    public String signup(@RequestBody UserDataDTO user) {
       AppUser userToSave= modelMapper.map(user, AppUser.class);
       userToSave.setAppUserRoles(AppUserRole.ROLE_CLIENT);
        return userService.signup(userToSave);
    }

    @DeleteMapping(value = "/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete( @PathVariable String email) {
        userService.delete(email);
        return email;
    }

    @GetMapping(value = "/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserResponseDTO searchByEmail(@PathVariable String email) {
        return modelMapper.map(userService.searchByEmail(email), UserResponseDTO.class);
    }

    @GetMapping(value = "/myInfo")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public UserResponseDTO getMyInfo(HttpServletRequest req) {
        return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
    }

    @GetMapping("/refresh")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public String refresh(HttpServletRequest req) {
        return userService.refresh(req.getRemoteUser());
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<AppUser> listAll() {
        return userService.listAll();
    }

}