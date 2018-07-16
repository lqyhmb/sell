package com.lqy.sell.service;

import com.lqy.sell.dto.OrderMasterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 订单Service
 * Created by Rodriguez
 * 2018/7/13 8:51
 */
public interface OrderMasterService {

    /**
     * 创建订单
     */
    OrderMasterDto create(OrderMasterDto orderMasterDto);

    /**
     * 查询单个订单
     */
    OrderMasterDto findOne(String orderId);

    /**
     * 查询某个人的订单列表
     */
    Page<OrderMasterDto> findList(String buyerOpenid, Pageable pageable);

    /**
     * 取消订单
     */
    OrderMasterDto cancel(OrderMasterDto orderMasterDto);

    /**
     * 完结订单
     */
    OrderMasterDto finish(OrderMasterDto orderMasterDto);

    /**
     * 支付订单
     */
    OrderMasterDto paid(OrderMasterDto orderMasterDto);

    /**
     * 查询所有订单列表
     */
    Page<OrderMasterDto> findList(Pageable pageable);

}
