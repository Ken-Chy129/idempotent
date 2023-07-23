package cn.ken.idempotent.core;

import org.redisson.api.RLock;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/7/23 0:18
 */
public final class IdempotentContext {

    private static final ThreadLocal<RLock> LOCK = new ThreadLocal<>();
    private static final ThreadLocal<String> HANDLER = new ThreadLocal<>();
    private static final ThreadLocal<String> STRATEGY = new ThreadLocal<>();
    
    public static RLock getLock() {
        return LOCK.get();
    }
    
    public static void setLock(RLock lock) {
        LOCK.set(lock);
    }

    public static String getHandler() {
        return HANDLER.get();
    }

    public static void setHandler(String handlerName) {
        HANDLER.set(handlerName);
    }
    
    public static String getStrategy() {
        return STRATEGY.get();
    }

    public static void setStrategy(String strategyName) {
        STRATEGY.set(strategyName);
    }

    public static void clean() {
        LOCK.remove();
        HANDLER.remove();
        STRATEGY.remove();
    }
}
