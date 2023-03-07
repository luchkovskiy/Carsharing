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
    private Float max_speed;
    private String color;
    private String current_location;
    private Integer issue_year;
    private String drive_type;
    private Float gas_consumption;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
