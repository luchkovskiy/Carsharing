package com.luchkovskiy.controllers.requests;

import com.luchkovskiy.models.Session;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccidentCreateRequest {

    private Session session;
    private String name;
    private Float fine;
    private Timestamp time;
    private Float ratingSubtraction;
    private Integer damageLevel;
    private Boolean critical;

}
