package cn.ken.dempotent.annotations;

import cn.ken.dempotent.enums.IdempotentTypeEnum;

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
    
    // 是否等待到获取锁执行
    boolean waitFor() default false; 
    
}
