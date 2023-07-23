package cn.ken.idempotent.autoconfigure;

import cn.ken.idempotent.core.IdempotentAspect;
import cn.ken.idempotent.core.IdempotentHandlerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/7/23 10:06
 */
@Configuration
public class IdempotentAutoConfiguration {
    
    @Bean
    public IdempotentAspect idempotentAspect() {
        return new IdempotentAspect();
    }
    
    @Bean
    public IdempotentHandlerFactory idempotentHandlerFactor() {
        return new IdempotentHandlerFactory();
    }
    
    @Configuration
    @Import(IdempotentHandlerConfiguration.class)
    protected static class IdempotentHandlerAutoConfiguration {
        
    }
}
