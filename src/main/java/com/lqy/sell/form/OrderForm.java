package com.lqy.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 订单form参数
 * Created by Rodriguez
 * 2018/7/13 17:07
 */
@Data
public class OrderForm {

    /**
     * 买家姓名
     */
    @NotEmpty(message = "姓名必填")
    private String name;

    /**
     * 买家手机号
     */
    @NotEmpty(message = "手机号必填")
    private String phone;

    /**
     * 买家地址
     */
    @NotEmpty(message = "地址必填")
    private String address;

    /**
     * 买家openid
     */
    @NotEmpty(message = "openid必填")
    private String openid;

    /**
     * 购物车信息
     */
    @NotEmpty(message = "购物车不能为空")
    private String items;

   /*name: "张三"
    phone: "18868822111"
    address: "慕课网总部"
    openid: "ew3euwhd7sjw9diwkq" //用户的微信openid
    items: [{
        productId: "1423113435324",
                productQuantity: 2 //购买数量
    }]*/
}
