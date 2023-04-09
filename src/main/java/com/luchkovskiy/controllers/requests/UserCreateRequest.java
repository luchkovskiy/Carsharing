package com.luchkovskiy.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateRequest {

    private Long id;
    private String name;
    private String surname;
    private Date birthdayDate;
    private Boolean active;
    private String address;
    private String passportId;
    private String driverId;
    private Float drivingExperience;
    private Float rating;
    private Float accountBalance;

}
