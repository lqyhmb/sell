package com.lqy.sell.controller;

import com.lqy.sell.VO.ProductInfoVO;
import com.lqy.sell.VO.ProductVO;
import com.lqy.sell.VO.ResultVO;
import com.lqy.sell.dataobject.ProductCategory;
import com.lqy.sell.dataobject.ProductInfo;
import com.lqy.sell.service.ProductCategoryService;
import com.lqy.sell.service.ProductInfoService;
import com.lqy.sell.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 * Created by Rodriguez
 * 2018/7/12 13:00
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ResultVO list() {
        // 1.查询所有上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        // 2.查询类目（一次性查询）
        List<Integer> categoryTypeList = new ArrayList<>();
        // 传统做法
        /*for (ProductInfo productInfo : upAll) {
            categoryTypeList.add(productInfo.getCategoryType());
        }*/
        // 精简方法： java8 写法
        categoryTypeList.addAll(productInfoList.stream().map(ProductInfo::getCategoryType).collect(Collectors.toList()));
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);

        // 3.数据封装成ResultVO
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            // 封装ProductVO
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productCategory.getCategoryType() == productInfo.getCategoryType()) {
                    // 封装ProductInfoVO
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }

            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        /*ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(productVOList);*/
        return ResultVOUtil.success(productVOList);
    }
}
