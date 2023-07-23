package cn.ken.idempotent.exceptions;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/7/23 10:49
 */
public class RepeatedRequestException extends RuntimeException {

    public RepeatedRequestException(String message) {
        super(message);
    }
}
