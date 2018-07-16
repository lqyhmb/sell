package com.lqy.sell.service.impl;

import com.lqy.sell.dataobject.OrderDetail;
import com.lqy.sell.dto.OrderMasterDto;
import com.lqy.sell.enums.OrderStatusEnum;
import com.lqy.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单测试
 * Created by Rodriguez
 * 2018/7/13 9:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterServiceImplTest {

    public static final String BUYER_OPENID = "110120";
    public static final String ORDER_ID = "1531466718958335658";

    @Autowired
    private OrderMasterServiceImpl orderMasterService;

    @Test
    public void create() throws Exception {
        OrderMasterDto orderMasterDto = new OrderMasterDto();
        orderMasterDto.setBuyerName("廖先生");
        orderMasterDto.setBuyerPhone("15243621661");
        orderMasterDto.setBuyerAddress("名门华府1302");
        orderMasterDto.setBuyerOpenid(BUYER_OPENID);

        // 购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("100003");
        o1.setProductQuantity(1);
        orderDetailList.add(o1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("100002");
        o2.setProductQuantity(10);
        orderDetailList.add(o2);

        orderMasterDto.setOrderDetailList(orderDetailList);

        OrderMasterDto result = orderMasterService.create(orderMasterDto);
        log.info("【创建订单】 result={}", result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() throws Exception {
        OrderMasterDto result = orderMasterService.findOne(ORDER_ID);
        log.info("【查询单个订单】 result={}", result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findList() throws Exception {
        PageRequest request = new PageRequest(0, 5);
        Page<OrderMasterDto> orderMasterDtoPage = orderMasterService.findList(BUYER_OPENID, request);
        Assert.assertNotEquals(0, orderMasterDtoPage.getContent().size());
    }

    @Test
    public void cancel() throws Exception {
        OrderMasterDto orderMasterDto = orderMasterService.findOne(ORDER_ID);
        OrderMasterDto cancel = orderMasterService.cancel(orderMasterDto);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), cancel.getOrderStatus());
    }

    @Test
    public void finish() throws Exception {
        OrderMasterDto orderMasterDto = orderMasterService.findOne(ORDER_ID);
        OrderMasterDto finish = orderMasterService.finish(orderMasterDto);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), finish.getOrderStatus());
    }

    @Test
    public void paid() throws Exception {
        OrderMasterDto orderMasterDto = orderMasterService.findOne(ORDER_ID);
        OrderMasterDto paid = orderMasterService.paid(orderMasterDto);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), paid.getPayStatus());
    }

    @Test
    public void findAllList() throws Exception {
        PageRequest request = new PageRequest(0, 5);
        Page<OrderMasterDto> orderMasterDtoPage = orderMasterService.findList(request);
        //Assert.assertNotEquals(0, orderMasterDtoPage.getContent().size());
        Assert.assertTrue("查询所有订单列表", orderMasterDtoPage.getContent().size() > 0);
    }
}