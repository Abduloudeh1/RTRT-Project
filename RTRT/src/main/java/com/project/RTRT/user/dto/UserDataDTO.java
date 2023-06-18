package com.project.RTRT.user.dto;
import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UserDataDTO {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthDay;


}