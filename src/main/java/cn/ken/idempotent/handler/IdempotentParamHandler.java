package cn.ken.idempotent.handler;

import cn.ken.idempotent.annotations.Idempotent;
import cn.ken.idempotent.annotations.KeyBody;
import cn.ken.idempotent.annotations.KeyParam;
import cn.ken.idempotent.annotations.KeyProperty;
import cn.ken.idempotent.core.IdempotentContext;
import cn.ken.idempotent.exceptions.KeyGenerateException;
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

        if (!lock.tryLock()) {
            // 获取不到锁，进入拒绝策略
            annotation.rejectStrategy().reject(lock);
            return;
        }
        // 获取到锁，保存到当前线程上下文方便后续释放
        IdempotentContext.setLock(lock);
    }

    @Override
    public void exceptionProcessing(Throwable e) {
        System.out.println(e.getMessage());
    }

    @Override
    public void postProcessing() {
        RLock lock = IdempotentContext.getLock();
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
