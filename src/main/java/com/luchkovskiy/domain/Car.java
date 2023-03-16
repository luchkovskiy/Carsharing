package com.luchkovskiy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Car {

    private Long id;
    private String brand;
    private String model;
    private Timestamp created;
    private Timestamp changed;
    private Boolean available;
    private Float maxSpeed;
    private String color;
    private String currentLocation;
    private Integer releaseYear;
    private String driveType;
    private Float gasConsumption;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
