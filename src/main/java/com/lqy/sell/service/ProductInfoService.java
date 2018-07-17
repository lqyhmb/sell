package com.lqy.sell.service;

import com.lqy.sell.dataobject.ProductInfo;
import com.lqy.sell.dto.CartDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品Service
 * Created by Rodriguez
 * 2018/7/12 12:37
 */
public interface ProductInfoService {

    ProductInfo findOne(String productInfoId);

    /**
     * 查询所有在架商品列表
     *
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 分页查询所有商品列表
     *
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    // 加库存
    void increaseStock(List<CartDto> cartDtoList);

    // 减库存
    void decreaseStock(List<CartDto> cartDtoList);

    // 上架
    ProductInfo onSale(String productId);

    // 下架
    ProductInfo offSale(String productId);
}
