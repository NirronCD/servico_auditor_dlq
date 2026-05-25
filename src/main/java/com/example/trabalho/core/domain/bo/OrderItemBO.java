package com.example.trabalho.core.domain.bo;

public class OrderItemBO {
    private final Integer sku;
    private final Integer amount;

    public OrderItemBO(Integer sku, Integer amount) {
        this.sku = sku;
        this.amount = amount;
    }

    public Integer getSku() { return sku; }
    public Integer getAmount() { return amount; }
}