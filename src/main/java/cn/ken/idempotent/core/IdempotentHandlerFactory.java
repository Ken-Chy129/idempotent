package cn.ken.idempotent.core;

import cn.ken.idempotent.enums.IdempotentTypeEnum;
import cn.ken.idempotent.handler.IdempotentHandler;
import cn.ken.idempotent.handler.IdempotentParamHandler;
import cn.ken.idempotent.handler.IdempotentTokenHandler;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/7/22 19:44
 */
public final class IdempotentHandlerFactory {
    
    public static IdempotentHandler getIdempotentHandler(IdempotentTypeEnum type) {
        IdempotentHandler result;
        switch (type) {
            case PARAM -> result = ApplicationContextHolder.getBean(IdempotentParamHandler.class);
            case TOKEN -> result = ApplicationContextHolder.getBean(IdempotentTokenHandler.class);
            case CUSTOM -> result = ApplicationContextHolder.getBean(IdempotentContext.getHandler(), IdempotentHandler.class);
            default -> throw new RuntimeException(String.format("幂等处理类型 [%s] 不存在", type.name()));
        }
        return result;
    }
}
