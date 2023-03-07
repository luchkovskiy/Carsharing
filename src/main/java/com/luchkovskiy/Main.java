package com.luchkovskiy;

import com.luchkovskiy.domain.Session;
import com.luchkovskiy.service.AccidentService;
import com.luchkovskiy.service.AggregationService;
import com.luchkovskiy.service.SessionService;
import com.luchkovskiy.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.luchkovskiy");
        AccidentService accidentService = applicationContext.getBean("accidentServiceImpl", AccidentService.class);
        UserService userService = applicationContext.getBean("userServiceImpl", UserService.class);
        AggregationService aggregationService = applicationContext.getBean("aggregationServiceImpl", AggregationService.class);
        SessionService sessionService = applicationContext.getBean("sessionServiceImpl", SessionService.class);
        System.out.println(accidentService.readAll());
        System.out.println(userService.checkIdExist(12L));
        System.out.println(aggregationService.deleteInactiveUser(0L));
        Session session = sessionService.create(new Session());
        System.out.println(session);
    }
}
