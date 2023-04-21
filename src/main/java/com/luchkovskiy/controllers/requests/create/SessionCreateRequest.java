package com.luchkovskiy.controllers.requests.create;

import com.luchkovskiy.models.Car;
import com.luchkovskiy.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SessionCreateRequest {

    private Long userId;
    private Long carId;
    private Timestamp startTime;
    private Timestamp endTime;
    private Float distancePassed;

}
