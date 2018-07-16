package com.lqy.sell.service;

import com.lqy.sell.dto.OrderMasterDto;

/**
 * 买家
 * Created by Rodriguez
 * 2018/7/16 11:33
 */
public interface BuyerService {

    // 查询一个订单
    OrderMasterDto findOrderOne(String openid, String orderId);

    // 取消订单
    OrderMasterDto cancelOrder(String openid, String orderId);

}
