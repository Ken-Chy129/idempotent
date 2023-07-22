package cn.ken.dempotent.core;

import org.redisson.api.RLock;

import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/7/23 0:18
 */
public final class IdempotentContext {

    private static final ThreadLocal<RLock> CONTEXT = new ThreadLocal<>();
    
    public static RLock get() {
        return CONTEXT.get();
    }
    
    public static void set(RLock lock) {
        CONTEXT.set(lock);
    }

    public static void clean() {
        CONTEXT.remove();
    }
}
