package cn.ken.dempotent.core;

import cn.ken.dempotent.annotation.Idempotent;
import cn.ken.dempotent.annotation.KeyBody;
import cn.ken.dempotent.annotation.KeyParam;
import cn.ken.dempotent.annotation.KeyProperty;
import cn.ken.dempotent.exceptions.IdempotentException;
import cn.ken.dempotent.exceptions.KeyGenerateException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/7/22 20:53
 */
public class IdempotentParamHandler implements IdempotentHandler {
    
    private final RedissonClient redissonClient;

    private final static String LOCK = "lock:param:";

    public IdempotentParamHandler(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void execute(ProceedingJoinPoint joinPoint, Method method) {
        String key;
        Idempotent annotation = method.getAnnotation(Idempotent.class);
        try {
            key = (annotation.keyPrefix().isBlank() ?
                    method.getName() : annotation.keyPrefix())
                    + ":" + getParameterKey(method.getParameters(), joinPoint.getArgs());
        } catch (IllegalAccessException e) {
            throw new KeyGenerateException();
        }
        RLock lock = redissonClient.getLock(LOCK + key);
        if (annotation.waitFor()) {
            lock.lock();
        } else {
            if (!lock.tryLock()) {
                throw new IdempotentException(annotation.message());
            }
        }
        IdempotentContext.set(lock);
    }

    @Override
    public void exceptionProcessing() {
        IdempotentHandler.super.exceptionProcessing();
    }

    @Override
    public void postProcessing() {
        RLock lock = IdempotentContext.get();
        lock.unlock();
    }

    private String getParameterKey(Parameter[] parameters, Object[] args) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getAnnotation(KeyBody.class) != null) {
                // 如果类用KeyBody注解标注，则获取类中所有带KeyProperty注解的属性的值加入key
                Field[] fields = parameters[i].getType().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getAnnotation(KeyProperty.class) != null) {
                        sb.append(field.get(args[i])).append(":");
                    }
                }
                continue;
            }
            // 如果类被KeyParam注解标注，则加入key
            if (parameters[i].getAnnotation(KeyParam.class) != null) {
                sb.append(args[i]).append(":");
            }
        }
        return sb.substring(0, sb.toString().length() - 1);
    }
}
