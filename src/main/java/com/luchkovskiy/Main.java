package com.luchkovskiy;

import com.luchkovskiy.domain.Accident;
import com.luchkovskiy.domain.Session;
import com.luchkovskiy.service.AccidentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.luchkovskiy");
        AccidentService accidentService = applicationContext.getBean("accidentServiceImpl", AccidentService.class);
        System.out.println(accidentService.checkIdExist(1L));


    }
}
