package com.lqy.sell.service.impl;

import com.lqy.sell.dto.OrderMasterDto;
import com.lqy.sell.enums.ResultEnum;
import com.lqy.sell.exception.SellException;
import com.lqy.sell.service.BuyerService;
import com.lqy.sell.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Rodriguez
 * 2018/7/16 11:34
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderMasterService orderMasterService;

    @Override
    public OrderMasterDto findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderMasterDto cancelOrder(String openid, String orderId) {
        OrderMasterDto orderMasterDto = checkOrderOwner(openid, orderId);
        if (orderMasterDto == null) {
            log.error("【取消订单】 查不到该订单,openid={},orderId={}", openid, orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderMasterService.cancel(orderMasterDto);
    }

    public OrderMasterDto checkOrderOwner(String openid, String orderId) {
        OrderMasterDto orderMasterDto = orderMasterService.findOne(orderId);
        if (orderMasterDto == null) {
            return null;
        }
        if (!orderMasterDto.getBuyerOpenid().equals(openid)) { // 不是本人的订单
            log.error("【查询订单】 订单的openid不一致,openid={},orderId={}", openid, orderId);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderMasterDto;
    }
}
