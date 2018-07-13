package com.lqy.sell.exception;

import com.lqy.sell.enums.ResultEnum;

/**
 * Created by Rodriguez
 * 2018/7/13 9:05
 */
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public SellException(ResultEnum resultEnum, String message) {
        super(message);
        this.code = resultEnum.getCode();
    }

}
