package cn.ken.idempotent.handler;

import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/7/22 20:54
 */
public class IdempotentTokenHandler implements IdempotentHandler {
    
    @Override
    public void execute(ProceedingJoinPoint joinPoint, Method method) {
        
    }

    @Override
    public void exceptionProcessing(Throwable e) {
        IdempotentHandler.super.exceptionProcessing(e);
    }

    @Override
    public void postProcessing() {
        IdempotentHandler.super.postProcessing();
    }
}
