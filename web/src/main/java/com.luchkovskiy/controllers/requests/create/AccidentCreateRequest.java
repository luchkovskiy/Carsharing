package com.luchkovskiy.controllers.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.*;

import javax.validation.constraints.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
public class AccidentCreateRequest {

    // TODO: 18.04.2023 Добавить валидацию

    @Size
    private Long sessionId;
    private String name;
    private Float fine;
    private Timestamp time;
    private Float ratingSubtraction;
    private Integer damageLevel;
    private Boolean critical;

}
