package com.lqy.sell.service.impl;

import com.lqy.sell.dataobject.SellerInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Rodriguez
 * 2018/7/18 9:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoServiceImplTest {

    public static final String openid = "abc";

    @Autowired
    private SellerInfoServiceImpl sellerInfoService;

    @Test
    public void findByOpenid() throws Exception {
        SellerInfo sellerInfo = sellerInfoService.findByOpenid(openid);
        Assert.assertNotNull(sellerInfo);
    }

}