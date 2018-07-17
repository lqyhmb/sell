package com.lqy.sell.controller;

import com.lqy.sell.dto.OrderMasterDto;
import com.lqy.sell.enums.ResultEnum;
import com.lqy.sell.exception.SellException;
import com.lqy.sell.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("order/list", map);
    }

    /**
     * 取消订单
     *
     * @param orderId
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        try {
            OrderMasterDto orderMasterDto = orderMasterService.findOne(orderId);
            orderMasterService.cancel(orderMasterDto);
        } catch (SellException e) {
            log.error("【卖家端取消订单】 发生异常: {}", e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * 订单详情
     *
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        OrderMasterDto orderMasterDto = new OrderMasterDto();
        try {
            orderMasterDto = orderMasterService.findOne(orderId);
        } catch (SellException e) {
            log.error("【卖家端订单详情】 发生异常: {}", e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("orderMasterDto", orderMasterDto);
        return new ModelAndView("order/detail", map);
    }

    /**
     * 完结订单
     *
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        try {
            OrderMasterDto orderMasterDto = orderMasterService.findOne(orderId);
            orderMasterService.finish(orderMasterDto);
        } catch (SellException e) {
            log.error("【卖家端完结订单】 发生异常: {}", e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }
}
