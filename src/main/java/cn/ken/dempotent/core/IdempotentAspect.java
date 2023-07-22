package cn.ken.dempotent.core;

import cn.ken.dempotent.annotation.Idempotent;
import cn.ken.dempotent.annotation.KeyBody;
import cn.ken.dempotent.annotation.KeyParam;
import cn.ken.dempotent.annotation.KeyProperty;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/7/22 19:16
 */
@Aspect
public class IdempotentAspect {

    @Around("@annotation(cn.ken.dempotent.annotation.Idempotent)")
    public Object idempotentHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        // 根据注解参数获取处理器实例
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        Idempotent idempotent = method.getAnnotation(Idempotent.class);
        IdempotentHandler idempotentHandler = IdempotentHandlerFactory.getIdempotentHand(idempotent.type(), idempotent.handlerName());
        Object result = null;
        try {
            idempotentHandler.execute(joinPoint, method);
            result = joinPoint.proceed();
            idempotentHandler.postProcessing();
        } catch (Throwable e) {
            idempotentHandler.exceptionProcessing();
        } finally {
            IdempotentContext.clean();
        }
        return result;
    }
}
