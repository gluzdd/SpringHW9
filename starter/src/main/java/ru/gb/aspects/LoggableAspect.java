package ru.gb.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggableAspect {



    @Around("@annotation(ru.gb.aspects.Timer ) || within(@ru.gb.aspects.Timer *)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
        Long startTime = System.currentTimeMillis();
        Object res = null;
        Long resTime = 0L;
        try {
            res = joinPoint.proceed();
        } catch (Throwable ex) {
            log.error("Exception caught: {}", ex.getMessage());
        } finally {
            Long endTime = System.currentTimeMillis();
            resTime = endTime - startTime;
            log.info("{} - {} #({} mseconds)", joinPoint.getTarget().getClass().getSimpleName(),
                                               joinPoint.getSignature().getName(), resTime / 1000.0);
        }
        return res;
    }
}
