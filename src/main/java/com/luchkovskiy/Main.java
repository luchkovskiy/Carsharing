package com.luchkovskiy;

import com.luchkovskiy.configuration.ApplicationConfig;
import com.luchkovskiy.service.AccidentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        AccidentService accidentService = applicationContext.getBean("accidentServiceImpl", AccidentService.class);
        System.out.println(accidentService.readAll());

    }
}
