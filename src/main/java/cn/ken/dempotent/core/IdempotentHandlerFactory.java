package cn.ken.dempotent.core;

import cn.ken.dempotent.enums.IdempotentTypeEnum;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/7/22 19:44
 */
public final class IdempotentHandlerFactory implements ApplicationContextAware {
    
    private static ApplicationContext CONTEXT;

    public static IdempotentHandler getIdempotentHand(IdempotentTypeEnum type, String handlerName) {
        IdempotentHandler result;
        switch (type) {
            case PARAM -> result = CONTEXT.getBean(IdempotentParamHandler.class);
            case TOKEN -> result = CONTEXT.getBean(IdempotentTokenHandler.class);
            case CUSTOM -> result = CONTEXT.getBean(handlerName, IdempotentHandler.class);
            default -> throw new RuntimeException(String.format("幂等处理类型 [%s] 不存在", type.name()));
        }
        return result;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CONTEXT = applicationContext;
    }
}
