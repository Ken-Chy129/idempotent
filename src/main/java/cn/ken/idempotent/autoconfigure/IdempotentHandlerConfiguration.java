package cn.ken.idempotent.autoconfigure;

import cn.ken.idempotent.handler.IdempotentParamHandler;
import cn.ken.idempotent.handler.IdempotentTokenHandler;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/7/23 10:14
 */
@Configuration
public class IdempotentHandlerConfiguration {
    
    @Bean
    public IdempotentParamHandler idempotentParamHandler(RedissonClient redissonClient) {
        return new IdempotentParamHandler(redissonClient);
    }
    
    @Bean
    public IdempotentTokenHandler idempotentTokenHandler() {
        return new IdempotentTokenHandler();
    }
}
