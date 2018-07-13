package com.lqy.sell.service.impl;

import com.lqy.sell.converter.OrderMaster2OrderMasterDtoConverter;
import com.lqy.sell.dataobject.OrderDetail;
import com.lqy.sell.dataobject.OrderMaster;
import com.lqy.sell.dataobject.ProductInfo;
import com.lqy.sell.dto.CartDto;
import com.lqy.sell.dto.OrderMasterDto;
import com.lqy.sell.enums.OrderStatusEnum;
import com.lqy.sell.enums.PayStatusEnum;
import com.lqy.sell.enums.ResultEnum;
import com.lqy.sell.exception.SellException;
import com.lqy.sell.repository.OrderDetailRepository;
import com.lqy.sell.repository.OrderMasterRepository;
import com.lqy.sell.repository.ProductInfoRepository;
import com.lqy.sell.service.OrderMasterService;
import com.lqy.sell.service.ProductInfoService;
import com.lqy.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单Service实现
 * Created by Rodriguez
 * 2018/7/13 8:59
 */
@Service
@Slf4j
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public OrderMasterDto create(OrderMasterDto orderMasterDto) {
        String orderId = KeyUtil.genUniqueKey(); // 订单id
        BigDecimal orderAmount = new BigDecimal(0); // 订单总金额
        //List<CartDto> cartDtoList = new ArrayList<>();
        // 1.查询商品 （数量 价格）
        for (OrderDetail orderDetail : orderMasterDto.getOrderDetailList()) {
            ProductInfo productInfo = productInfoRepository.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            // 2. 计算总价  商品总价=查询的商品单价*订单详情的商品数量+订单总金额
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            // 订单详情入库
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
            //CartDto cartDto = new CartDto(orderDetail.getProductId(),orderDetail.getProductQuantity());
            //cartDtoList.add(cartDto);
        }
        // 3. 写入订单数据库（OrderMaster 和 OrderDetail）
        OrderMaster orderMaster = new OrderMaster();
        orderMasterDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderMasterDto, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        // 4. 扣库存
        List<CartDto> cartDtoList = orderMasterDto.getOrderDetailList().stream()
                .map(e -> new CartDto(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.decreaseStock(cartDtoList);
        return orderMasterDto;
    }

    @Override
    public OrderMasterDto findOne(String orderId) {
        // 查询订单信息
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 查询订单详情列表
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        // 封装OrderMasterDto
        OrderMasterDto orderMasterDto = new OrderMasterDto();
        BeanUtils.copyProperties(orderMaster, orderMasterDto);
        orderMasterDto.setOrderDetailList(orderDetailList);
        return orderMasterDto;
    }

    @Override
    public Page<OrderMasterDto> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderMasterDto> orderMasterDtoList = OrderMaster2OrderMasterDtoConverter.convert(orderMasterPage.getContent());
        return new PageImpl<OrderMasterDto>(orderMasterDtoList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderMasterDto cancel(OrderMasterDto orderMasterDto) {
        OrderMaster orderMaster = new OrderMaster();
        // 判断订单状态
        if (!orderMasterDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】 订单状态不正确,orderId={},orderStatus={}", orderMasterDto.getOrderId(), orderMasterDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        orderMasterDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderMasterDto, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【取消订单】 更新失败,orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        // 返还库存
        if (CollectionUtils.isEmpty(orderMasterDto.getOrderDetailList())) {
            log.error("【取消订单】 订单中无商品详情,orderMasterDto={}", orderMasterDto);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDto> cartDtoList = orderMasterDto.getOrderDetailList().stream()
                .map(e -> new CartDto(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(cartDtoList);
        // 如果已支付,需要退款
        if (orderMasterDto.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            // TODO
        }
        return orderMasterDto;
    }

    @Override
    @Transactional
    public OrderMasterDto finish(OrderMasterDto orderMasterDto) {
        // 判断订单状态
        if (!orderMasterDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】 订单状态不正确, orderId={},orderStatus={}", orderMasterDto.getOrderId(), orderMasterDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderMasterDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderMasterDto, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【完结订单】 更新失败,orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderMasterDto;
    }

    @Override
    @Transactional
    public OrderMasterDto paid(OrderMasterDto orderMasterDto) {
        // 判断订单状态
        if (!orderMasterDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付成功】 订单状态不正确, orderId={},orderStatus={}", orderMasterDto.getOrderId(), orderMasterDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 判断支付状态
        if (!orderMasterDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付成功】 订单支付状态不正确,orderMasterDto={}", orderMasterDto);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        // 修改支付状态
        OrderMaster orderMaster = new OrderMaster();
        orderMasterDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderMasterDto,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【订单支付成功】 更新失败,orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderMasterDto;
    }
}
