package com.lqy.sell.controller;

import com.lqy.sell.dto.OrderMasterDto;
import com.lqy.sell.service.OrderMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家端订单
 * Created by Rodriguez
 * 2018/7/16 18:05
 */
@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    /**
     * 订单列表
     *
     * @param page 第几页，第一页开始
     * @param size 每一页展示多少条数据
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(name = "page", defaultValue = "1") Integer page,
                             @RequestParam(name = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<OrderMasterDto> orderMasterDtoPage = orderMasterService.findList(pageRequest);
        map.put("orderMasterDtoPage", orderMasterDtoPage);
        return new ModelAndView("order/list", map);
    }

}
