package com.lqy.sell.enums;

import lombok.Getter;

/**
 * 订单状态
 * Created by Rodriguez
 * 2018/7/12 18:52
 */
@Getter
public enum OrderStatusEnum {
    NEW(0, "新订单"), FINISH(1, "完结"), CANCLE(2, "取消");

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
