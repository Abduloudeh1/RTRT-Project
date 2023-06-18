package com.project.RTRT.security.dto;
import java.util.List;

import com.project.RTRT.security.model.AppUserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDataDTO {

    private String email;

    private String password;

    List<AppUserRole> appUserRoles;

}