package cn.ken.idempotent.demo;

import cn.ken.idempotent.annotations.Idempotent;
import cn.ken.idempotent.annotations.KeyBody;
import cn.ken.idempotent.enums.IdempotentStrategyEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/7/23 0:34
 */
@RestController
public class TestController {

    @GetMapping("/test")
    @Idempotent(
            rejectStrategy = IdempotentStrategyEnum.REJECTED
    )
    public String idempotentHttpTest(@RequestBody @KeyBody Order order) throws InterruptedException {
        System.out.println(order);
        Thread.sleep(10000);
        return "success";
    }
}
