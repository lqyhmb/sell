package com.lqy.sell.VO;

import lombok.Data;

/**
 * http请求返回最外层对象
 * Created by Rodriguez
 * 2018/7/12 13:03
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO<T> {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 具体内容
     */
    private T data;

}
