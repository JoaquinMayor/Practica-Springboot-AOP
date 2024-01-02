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
@Aspect //ver una implementacion en el archivo pom.xml
@Component
public class GreetingAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass()); // Para registrar eventos

    

    //Este metodo se llama antes de ejecutar el metodo con el que se encuentra enlazado
    @Before("GreetingServicePointcuts.greetingLoggerPointCut()") //Enlace con el metodo indicamos clase y el pointcut
    public void loggerBefore(JoinPoint joinPoint){  //Une el joinpoint con la llamada a un metodo
        String method = joinPoint.getSignature().getName(); 
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info("Antes: " + method + " con los argumentos " + args);
    }

    //Este metodo se llama despues de ejecutar el metodo con el que se encuentra enlazado
    @After("GreetingServicePointcuts.greetingLoggerPointCut()") //Enlace con el metodo
    public void loggerAfter(JoinPoint joinPoint){  //Une el joinpoint con la llamada a un metodo
        String method = joinPoint.getSignature().getName(); 
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info("Despues: " + method + " con los argumentos " + args);
    }

    //Despues de un retorno
    @AfterReturning("GreetingServicePointcuts.greetingLoggerPointCut()") //Enlace con el metodo
    public void loggerAfterReturning(JoinPoint joinPoint){  //Une el joinpoint con la llamada a un metodo
        String method = joinPoint.getSignature().getName(); 
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info("Despues de retornar: " + method + " con los argumentos " + args);
    }

    //despues de lanzar una excepcion
    @AfterThrowing("execution(String com.joaquin.curso.springboot.aop.springbootaop.services.GreetingService.sayHelloError(..))") //Enlace con el metodo
    public void loggerAfterThrowing(JoinPoint joinPoint){  //Une el joinpoint con la llamada a un metodo
        String method = joinPoint.getSignature().getName(); 
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info("Despues de lanzar la excepcion: " + method + " con los argumentos " + args);
    }

    //Se ejecuta en el antes y despues, funciona como una combinacion entre before y after
    @Around("GreetingServicePointcuts.greetingLoggerPointCut()")
    public Object loggerAround(ProceedingJoinPoint joinPoint){
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        Object result = null;
        try {
            //Se anota que quieres hacer antes
            logger.info("El metodo: " + method + " con los parametros " + args);
             result = joinPoint.proceed(); //devuelve el result del metodo 
             //Se anota que se quiere hacer despues

             logger.info("El metodo: " + method + " retorna el resultado " + result);
            return result;
        } catch (Throwable e) {
            logger.error("Error en la llamada del metodo " + method);
        }
        return result;
    }

}
