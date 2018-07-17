package com.lqy.sell.enums;

import lombok.Getter;

/**
 * 商品状态
 * Created by Rodriguez
 * 2018/7/12 12:42
 */
@Getter
public enum ProductStatusEnum implements CodeEnum{
    UP(0, "上架"), DOWN(1, "下架");

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
