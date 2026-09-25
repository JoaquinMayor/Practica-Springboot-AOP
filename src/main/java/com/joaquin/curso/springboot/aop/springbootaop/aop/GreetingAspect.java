package com.joaquin.curso.springboot.aop.springbootaop.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2) //Determina el orden en el que se van a ejecutar los distintos aspectos creados para mismas funciones
@Aspect //ver una implementación en el archivo pom.xml
@Component
public class GreetingAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass()); // Para registrar eventos

    

    //Este método se llama antes de ejecutar el método con el que se encuentra enlazado
    @Before("GreetingServicePointcuts.greetingLoggerPointCut()") //Enlace con el método indicamos clase y el pointcut
    public void loggerBefore(JoinPoint joinPoint){  //Une el joinpoint con la llamada a un método
        String method = joinPoint.getSignature().getName(); 
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info("Antes: " + method + " con los argumentos " + args);
    }

    //Este método se llama después de ejecutar el método con el que se encuentra enlazado
    @After("GreetingServicePointcuts.greetingLoggerPointCut()") //Enlace con el método
    public void loggerAfter(JoinPoint joinPoint){  //Une el joinpoint con la llamada a un método
        String method = joinPoint.getSignature().getName(); 
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info("Después: " + method + " con los argumentos " + args);
    }

    //Después de un retorno
    @AfterReturning("GreetingServicePointcuts.greetingLoggerPointCut()") //Enlace con el método
    public void loggerAfterReturning(JoinPoint joinPoint){  //Une el joinpoint con la llamada a un método
        String method = joinPoint.getSignature().getName(); 
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info("Después de retornar: " + method + " con los argumentos " + args);
    }

    //después de lanzar una excepción
    @AfterThrowing("execution(String com.joaquin.curso.springboot.aop.springbootaop.services.GreetingService.sayHelloError(..))") //Enlace con el método
    public void loggerAfterThrowing(JoinPoint joinPoint){  //Une el joinpoint con la llamada a un método
        String method = joinPoint.getSignature().getName(); 
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info("Después de lanzar la excepción: " + method + " con los argumentos " + args);
    }

    //Se ejecuta en el antes y después, funciona como una combinación entre before y after
    @Around("GreetingServicePointcuts.greetingLoggerPointCut()")
    public Object loggerAround(ProceedingJoinPoint joinPoint){
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        Object result = null;
        try {
            //Se anota qué quieres hacer antes
            logger.info("El método: " + method + " con los parámetros " + args);
             result = joinPoint.proceed(); //devuelve el result del método 
             //Se anota qué se quiere hacer después

             logger.info("El método: " + method + " retorna el resultado " + result);
            return result;
        } catch (Throwable e) {
            logger.error("Error en la llamada del método " + method);
        }
        return result;
    }

}
