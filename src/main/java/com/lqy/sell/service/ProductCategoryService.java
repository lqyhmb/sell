package com.lqy.sell.service;

import com.lqy.sell.dataobject.ProductCategory;

import java.util.List;

/**
 * 类目Service
 * Created by Rodriguez
 * 2018/7/12 9:38
 */
public interface ProductCategoryService {

    ProductCategory findOne(Integer productCategoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
