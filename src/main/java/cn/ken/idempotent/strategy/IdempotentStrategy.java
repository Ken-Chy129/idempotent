package cn.ken.idempotent.strategy;

import org.redisson.api.RLock;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/7/23 10:42
 */
public interface IdempotentStrategy {
    
    void reject(RLock lock);
}
