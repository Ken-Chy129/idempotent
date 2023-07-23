package cn.ken.idempotent.demo;

import cn.ken.idempotent.annotations.KeyProperty;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/7/23 11:50
 */
public class Order {
    
    @KeyProperty
    private Integer userId;
    
    @KeyProperty
    private Integer productId;
    
    
    private String productName;

    public Order() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Order(Integer userId, Integer productId, String productName) {
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId=" + userId +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                '}';
    }
}
