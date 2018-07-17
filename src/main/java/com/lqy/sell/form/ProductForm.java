package com.lqy.sell.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Rodriguez
 * 2018/7/17 18:08
 */
@Data
public class ProductForm {

    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 商品库存
     */
    private Integer productStock;

    /**
     * 商品描述
     */
    private String productDescription;

    /**
     * 商品图片
     */
    private String productIcon;

    /**
     * 类目编号
     */
    private Integer categoryType;

}
