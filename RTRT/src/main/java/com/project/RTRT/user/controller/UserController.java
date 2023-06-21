package com.project.RTRT.user.controller;

import com.project.RTRT.user.dto.ErrorMessage;
import com.project.RTRT.user.dto.UserDataDTO;
import com.project.RTRT.user.dto.UserResponseDTO;
import com.project.RTRT.user.dto.UserSignInDTO;
import com.project.RTRT.user.dto.UserToken;
import com.project.RTRT.user.model.AppUser;
import com.project.RTRT.user.model.AppUserRole;
import com.project.RTRT.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

   private final UserService userService;

   private final ModelMapper modelMapper;

   @PostMapping("/signin")
   public ResponseEntity<?> login(@RequestBody UserSignInDTO userSignInDTO) {
      String signinToken = userService.signin(userSignInDTO.getEmail(), userSignInDTO.getPassword());
      UserToken userToken = new UserToken();
      userToken.setToken(signinToken);
      return new ResponseEntity<>(userToken, HttpStatus.OK);
   }

   @PostMapping("/signup")
   public ResponseEntity<?> signup(@RequestBody UserDataDTO user) {

      if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
         ErrorMessage errorMessage = new ErrorMessage();
         errorMessage.setMessage("Invalid Email");
         return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
      }
      AppUser userToSave = modelMapper.map(user, AppUser.class);
      userToSave.setRegistered(true);
      userToSave.setAppUserRoles(AppUserRole.ROLE_CLIENT);
      String signupToken = userService.signup(userToSave);
      UserToken userToken = new UserToken();
      userToken.setToken(signupToken);

      return new ResponseEntity<>(userToken, HttpStatus.OK);
   }

   @DeleteMapping(value = "/{email}")
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   public String delete(@PathVariable String email) {
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
      return modelMapper.map(userService.getMyInfo(req), UserResponseDTO.class);
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