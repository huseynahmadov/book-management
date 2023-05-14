package az.company.msbook.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* az.company.msbook.service..*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String message = String.format("Method '%s' called with parameters: %s", methodName, Arrays.toString(args));
        System.out.println(message);
    }

    @Pointcut("execution(* az.company.msbook.service..*.*(..))")
    public void methodsToLog() {
    }

    @After("methodsToLog()")
    public void logAfterMethodExecution(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        System.out.println("Method executed: " + className + "." + methodName);
        System.out.println("Input parameters: " + Arrays.toString(arguments));
    }

}
