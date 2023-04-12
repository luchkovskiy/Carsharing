package com.luchkovskiy.controllers.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccidentCreateRequest {

    private Long id;
    private Long sessionId;
    private String name;
    private Float fine;
    private Timestamp time;
    private Float ratingSubtraction;
    private Integer damageLevel;
    private Boolean critical;

}
