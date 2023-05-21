package com.luchkovskiy.aspects;

import com.luchkovskiy.service.ScheduledService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ScheduledAspect {

    private final ScheduledService scheduledService;

    @Before("execution(* com.luchkovskiy.service.implementations.SessionServiceImpl.endSession())")
    public void runScheduledTask() {
        scheduledService.inactiveSubscriptions();
    }

}
