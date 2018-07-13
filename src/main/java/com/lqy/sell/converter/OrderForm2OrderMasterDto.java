package com.lqy.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lqy.sell.dataobject.OrderDetail;
import com.lqy.sell.dto.OrderMasterDto;
import com.lqy.sell.enums.ResultEnum;
import com.lqy.sell.exception.SellException;
import com.lqy.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rodriguez
 * 2018/7/13 17:16
 */
@Slf4j
public class OrderForm2OrderMasterDto {

    public static OrderMasterDto convert(OrderForm orderForm) {
        OrderMasterDto orderMasterDto = new OrderMasterDto();
        orderMasterDto.setBuyerName(orderForm.getName());
        orderMasterDto.setBuyerPhone(orderForm.getPhone());
        orderMasterDto.setBuyerAddress(orderForm.getAddress());
        orderMasterDto.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        } catch (Exception e) {
            log.error("【对象转换】 错误,string={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderMasterDto.setOrderDetailList(orderDetailList);
        return orderMasterDto;
    }

}
