package com.lqy.sell.controller;

import com.lqy.sell.dataobject.ProductCategory;
import com.lqy.sell.dataobject.ProductInfo;
import com.lqy.sell.exception.SellException;
import com.lqy.sell.service.ProductCategoryService;
import com.lqy.sell.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 卖家端商品
 * Created by Rodriguez
 * 2018/7/17 16:31
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 商品列表
     *
     * @param page 第几页，默认第一页开始
     * @param size 每一页展示多少条数据
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(name = "page", defaultValue = "1") Integer page,
                             @RequestParam(name = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(pageRequest);
        List<ProductCategory> productCategoryList = productCategoryService.findAll();
        map.put("productInfoPage", productInfoPage);
        map.put("productCategoryList", productCategoryList);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("product/list", map);
    }

    /**
     * 上架商品
     *
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/onSale")
    public ModelAndView onSale(@RequestParam(name = "productId") String productId,
                               Map<String, Object> map) {
        try {
            ProductInfo productInfo = productInfoService.onSale(productId);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * 下架商品
     *
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/offSale")
    public ModelAndView offSale(@RequestParam(name = "productId") String productId,
                                Map<String, Object> map) {
        try {
            ProductInfo productInfo = productInfoService.offSale(productId);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }
}
