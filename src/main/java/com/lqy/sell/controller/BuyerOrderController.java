package com.lqy.sell.controller;

import com.lqy.sell.VO.ResultVO;
import com.lqy.sell.converter.OrderForm2OrderMasterDto;
import com.lqy.sell.dto.OrderMasterDto;
import com.lqy.sell.enums.ResultEnum;
import com.lqy.sell.exception.SellException;
import com.lqy.sell.form.OrderForm;
import com.lqy.sell.service.BuyerService;
import com.lqy.sell.service.OrderMasterService;
import com.lqy.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单
 * Created by Rodriguez
 * 2018/7/13 17:03
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private BuyerService buyerService;

    // 创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { // 表单校验是否有错误
            log.error("【创建订单】 参数不正确，orderForm={}", orderForm);
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST,
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderMasterDto orderMasterDto = OrderForm2OrderMasterDto.convert(orderForm);
        if (CollectionUtils.isEmpty(orderMasterDto.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderMasterDto createResult = orderMasterService.create(orderMasterDto);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());
        return ResultVOUtil.success(map);
    }

    // 订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderMasterDto>> list(@RequestParam("openid") String openid,
                                               @RequestParam(name = "page", defaultValue = "0") Integer page,
                                               @RequestParam(name = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】 openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(page, size);
        Page<OrderMasterDto> orderMasterDtoPage = orderMasterService.findList(openid, pageRequest);
        // 转存 Date -> Long   时间返回给前端多了毫秒值
        return ResultVOUtil.success(orderMasterDtoPage.getContent());
    }

    // 订单详情（查看单个订单）
    @GetMapping("/detail")
    public ResultVO<OrderMasterDto> detail(@RequestParam("openid") String openid, @RequestParam("orderId") String orderId) {
        OrderMasterDto orderMasterDto = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderMasterDto);
    }

    // 取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid, @RequestParam("orderId") String orderId) {
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }

}
