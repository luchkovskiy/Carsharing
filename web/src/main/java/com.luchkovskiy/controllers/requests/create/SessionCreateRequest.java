package com.luchkovskiy.controllers.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SessionCreateRequest {

    private Long userId;
    private Long carId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String status;

    private Float distancePassed;

}
