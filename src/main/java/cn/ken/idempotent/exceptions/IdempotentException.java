package cn.ken.idempotent.exceptions;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/7/23 0:16
 */
public class IdempotentException extends RuntimeException {

    public IdempotentException(String message) {
        super(message);
    }
}
