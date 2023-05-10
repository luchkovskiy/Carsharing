package com.luchkovskiy.controllers.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
public class AccidentCreateRequest {

    @NotBlank
    private Long sessionId;

    @Max(30)
    @NotBlank
    private String name;

    @NotBlank
    private Float fine;

    @NotBlank
    @PastOrPresent
    private LocalDateTime time;

    @NotBlank
    private Float ratingSubtraction;

    @NotBlank
    private Integer damageLevel;

    @NotEmpty
    private Boolean critical;

}
