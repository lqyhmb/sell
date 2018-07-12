package com.lqy.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息
 * Created by Rodriguez
 * 2018/7/12 12:23
 */
@Entity
@Data
public class ProductInfo {

    /**
     * 商品id
     */
    @Id
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
     * 商品状态  0正常 1下架
     */
    private Integer productStatus;

    /**
     * 类目编号
     */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;
}
