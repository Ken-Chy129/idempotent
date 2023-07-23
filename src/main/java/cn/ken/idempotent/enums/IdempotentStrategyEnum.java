package cn.ken.idempotent.enums;

import cn.ken.idempotent.core.ApplicationContextHolder;
import cn.ken.idempotent.core.IdempotentContext;
import cn.ken.idempotent.exceptions.RepeatedRequestException;
import cn.ken.idempotent.strategy.IdempotentStrategy;
import org.redisson.api.RLock;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/7/23 10:42
 */
public enum IdempotentStrategyEnum implements IdempotentStrategy {
    
    REJECTED {
        @Override
        public void reject(RLock lock) {
            throw new RepeatedRequestException();
        }
    },
    
    WAIT {
        @Override
        public void reject(RLock lock) {
            // todo:引入尝试等待时间
            lock.lock();
            IdempotentContext.setLock(lock);
        }
    },
    
    CUSTOM {
        @Override
        public void reject(RLock lock) {
            IdempotentStrategy customStrategy = ApplicationContextHolder.getBean(IdempotentContext.getStrategy(), IdempotentStrategy.class);
            customStrategy.reject(lock);
        }
    }
    
    ;

    
}
