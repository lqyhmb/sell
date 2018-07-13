package com.lqy.sell.dto;

import lombok.Data;

/**
 * 购物车
 * Created by Rodriguez
 * 2018/7/13 9:29
 */
@Data
public class CartDto {

    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品数量
     */
    private Integer productQuantity;

    public CartDto() {
    }

    public CartDto(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
