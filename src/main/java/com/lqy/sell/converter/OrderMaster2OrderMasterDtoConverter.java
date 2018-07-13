package com.lqy.sell.converter;

import com.lqy.sell.dataobject.OrderMaster;
import com.lqy.sell.dto.OrderMasterDto;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rodriguez
 * 2018/7/13 15:43
 */
public class OrderMaster2OrderMasterDtoConverter {

    public static OrderMasterDto convert(OrderMaster orderMaster) {
        OrderMasterDto orderMasterDto = new OrderMasterDto();
        BeanUtils.copyProperties(orderMaster, orderMasterDto);
        return orderMasterDto;
    }

    public static List<OrderMasterDto> convert(List<OrderMaster> orderMasterList) {
        return orderMasterList.stream().map(e -> convert(e)).collect(Collectors.toList());
    }


}
