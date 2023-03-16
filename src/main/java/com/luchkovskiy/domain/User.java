package com.luchkovskiy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Date;
import java.sql.Timestamp;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    private Long id;
    private String name;
    private String surname;
    private Date birthdayDate;
    private Timestamp created;
    private Timestamp changed;
    private Boolean active;
    private String address;
    private String passportId;
    private String driverId;
    private Float drivingExperience;
    private Integer roleId;
    private Float rating;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
