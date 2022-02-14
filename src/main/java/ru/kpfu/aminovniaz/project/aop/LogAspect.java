package ru.kpfu.aminovniaz.project.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.kpfu.aminovniaz.project.exception.NotFoundException;

import java.util.Date;

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @AfterThrowing(pointcut = "@annotation(ExceptionLog)", throwing = "exception")
    public void exceptionLogExecution(NotFoundException exception) {
        logger.info(new Date().toString());
        logger.info("New NotFoundException with message: ");
        logger.info(exception.getEntity());

        System.out.println("Logging info with new message: " + exception.getEntity());
    }

    @AfterThrowing(pointcut = "execution(* getGamePage(..))", throwing = "exception")
    public void exceptionLog(Exception exception) {
        logger.info(new Date().toString());
        logger.info("New NotFoundException with message: ");
        logger.info(exception.getMessage());

        System.out.println("Logging info with new message: " + exception.getMessage());
    }
}
