package com.luchkovskiy.controllers.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
public class AccidentCreateRequest {

    // TODO: 18.04.2023 Добавить валидацию

    private Long sessionId;
    private String name;
    private Float fine;
    private LocalDateTime time;
    private Float ratingSubtraction;
    private Integer damageLevel;
    private Boolean critical;

}
