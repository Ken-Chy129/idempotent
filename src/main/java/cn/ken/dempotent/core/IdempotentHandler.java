package cn.ken.dempotent.core;

import cn.ken.dempotent.annotation.Idempotent;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/7/22 20:39
 */
public interface IdempotentHandler {

    /**
     * 幂等处理逻辑
     *
     */
    void execute(ProceedingJoinPoint joinPoint, Method method);

    /**
     * 异常流程处理
     */
    default void exceptionProcessing() {

    }

    /**
     * 后置处理
     */
    default void postProcessing() {

    }
}
