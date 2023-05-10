package com.luchkovskiy.controllers.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
public class UserCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotEmpty
    @Past
    private Date birthdayDate;

    @NotEmpty
    private Boolean active;

    @NotBlank
    private String address;

    @NotBlank
    private String passportId;

    @NotBlank
    private String driverId;

    @NotEmpty
    private Float drivingExperience;

    @NotEmpty
    private Float rating;

    @NotEmpty
    private Float accountBalance;

    @Email
    private String email;

    @NotBlank
    private String password;

}
