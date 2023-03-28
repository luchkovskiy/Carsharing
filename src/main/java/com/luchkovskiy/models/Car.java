package com.luchkovskiy.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Car {

    private Long id;
    private String brand;
    private String model;
    private Timestamp created;
    private Timestamp changed;
    private Boolean visible;
    private Float maxSpeed;
    private String color;
    private Integer releaseYear;
    private String gearboxType;
    private Integer sitsAmount;
    private Integer classId;
    private Float gasConsumption;
    private String licensePlateNumber;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
