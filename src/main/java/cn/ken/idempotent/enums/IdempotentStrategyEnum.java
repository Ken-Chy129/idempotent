package cn.ken.idempotent.enums;

import cn.ken.idempotent.core.ApplicationContextHolder;
import cn.ken.idempotent.core.IdempotentContext;
import cn.ken.idempotent.exceptions.RepeatedRequestException;
import cn.ken.idempotent.strategy.IdempotentStrategy;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            throw new RepeatedRequestException("重复请求触发拒绝策略，请求被抛弃");
        }
    },
    
    WAIT {
        
        private static final Logger logger = LoggerFactory.getLogger(IdempotentStrategy.class);
        
        @Override
        public void reject(RLock lock) {
            // todo:引入尝试等待时间
            logger.warn("重复请求触发等待策略，将等待上一个请求完成");
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
    },
    
}
