package com.joaquin.curso.springboot.aop.springbootaop.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Aspect
@Component
public class GreetingFooAspect {
    private Logger logger = LoggerFactory.getLogger(getClass());

    

    @Before("GreetingServicePointcuts.greetingFooLoggerPointCut()") //Enlace con el método
    public void loggerBefore(JoinPoint joinPoint){  //Une el joinpoint con la llamada a un método
        String method = joinPoint.getSignature().getName(); 
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info("Antes Foo: " + method + " invocado con los parámetros " + args);
    }

     @After("GreetingServicePointcuts.greetingFooLoggerPointCut()") //Enlace con el método
    public void loggerAfter(JoinPoint joinPoint){  //Une el joinpoint con la llamada a un método
        String method = joinPoint.getSignature().getName(); 
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info("Despues Foo: " + method + " invocado con los parámetros " + args);
    }
}
