package cn.ken.idempotent.annotations;

import cn.ken.idempotent.enums.IdempotentStrategyEnum;
import cn.ken.idempotent.enums.IdempotentTypeEnum;

import java.lang.annotation.*;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/7/22 16:15
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {
    
    // 触发幂等失败逻辑时，返回的错误提示信息
    String message() default "您操作太快，请稍后再试";
    
    // 验证幂等类型，支持多种幂等方式，默认为参数验证
    IdempotentTypeEnum type() default IdempotentTypeEnum.PARAM;
    
    // 自定义handler名，在type为CUSTOM时使用
    String handlerName() default "";
    
    // key前缀，默认为方法名
    String keyPrefix() default "";
    
    // 重复请求处理方式，默认策略为拒绝
    IdempotentStrategyEnum rejectStrategy() default IdempotentStrategyEnum.REJECTED;
    
    // 自定义拒绝策略名，在拒绝策略类型为CUSTOM时使用
    String strategyName() default "";

}
