package cn.ken.idempotent.core;

import cn.ken.idempotent.annotations.Idempotent;
import cn.ken.idempotent.enums.IdempotentStrategyEnum;
import cn.ken.idempotent.enums.IdempotentTypeEnum;
import cn.ken.idempotent.handler.IdempotentHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

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
    
    @Around("@annotation(cn.ken.idempotent.annotations.Idempotent)")
    public Object idempotentHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        // 根据注解参数获取处理器实例
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        Idempotent idempotent = method.getAnnotation(Idempotent.class);
        IdempotentHandler idempotentHandler = IdempotentHandlerFactory.getIdempotentHandler(idempotent.type());
        
        // 保存自定义控制器或策略名到线程上下文
        if (idempotent.type() == IdempotentTypeEnum.CUSTOM) {
            IdempotentContext.setHandler(idempotent.handlerName());
        }
        if (idempotent.rejectStrategy() == IdempotentStrategyEnum.CUSTOM) {
            IdempotentContext.setStrategy(idempotent.strategyName());
        }
        
        Object result = null;
        try {
            idempotentHandler.execute(joinPoint, method);
            result = joinPoint.proceed();
            idempotentHandler.postProcessing();
        } catch (Throwable e) {
            idempotentHandler.exceptionProcessing(e);
        } finally {
            IdempotentContext.clean();
        }
        return result;
    }
}
