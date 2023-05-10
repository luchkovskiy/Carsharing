package com.luchkovskiy.controllers.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
public class SessionCreateRequest {

    @NotEmpty
    private Long userId;

    @NotEmpty
    private Long carId;

    @NotEmpty
    @PastOrPresent
    private LocalDateTime startTime;

    @NotEmpty
    @PastOrPresent
    private LocalDateTime endTime;

    @NotBlank
    private String status;

    @NotEmpty
    private Float distancePassed;

}
