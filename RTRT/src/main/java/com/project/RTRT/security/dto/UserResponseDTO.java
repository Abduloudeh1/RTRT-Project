package com.project.RTRT.security.dto;


import java.util.List;

import com.project.RTRT.security.model.AppUserRole;
import lombok.Data;

@Data
public class UserResponseDTO {

    private Integer id;
    private String username;
    private String email;
    List<AppUserRole> appUserRoles;

}