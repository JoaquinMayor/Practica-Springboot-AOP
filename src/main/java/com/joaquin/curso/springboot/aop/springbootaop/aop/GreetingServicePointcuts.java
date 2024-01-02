package com.joaquin.curso.springboot.aop.springbootaop.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GreetingServicePointcuts {
    @Pointcut("execution(String com.joaquin.curso.springboot.aop.springbootaop.services.GreetingService.sayHello(..))") //es una facil reutilizacion del package para los aspectos
    public void greetingLoggerPointCut(){}

    @Pointcut("execution(String com.joaquin.curso.springboot.aop.springbootaop.services.GreetingService.sayHello(..))") //es una facil reutilizacion del package para los aspectos
    public void greetingFooLoggerPointCut(){} //Los nombre tienen que ser diferentes en diferentes clases
}
