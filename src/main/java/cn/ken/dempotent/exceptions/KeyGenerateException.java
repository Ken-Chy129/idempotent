package cn.ken.dempotent.exceptions;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/7/23 0:03
 */
public class KeyGenerateException extends RuntimeException {

    public KeyGenerateException() {
        super("分布式锁键值构造失败");
    }
}
