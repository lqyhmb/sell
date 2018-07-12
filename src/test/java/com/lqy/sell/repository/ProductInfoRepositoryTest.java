package com.lqy.sell.repository;

import com.lqy.sell.dataobject.ProductInfo;
import com.lqy.sell.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品dao测试
 * Created by Rodriguez
 * 2018/7/12 12:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    ProductInfoRepository repository;

    @Test
    public void saveTest() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("100001");
        productInfo.setProductName("皮蛋粥");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很好喝的粥");
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);

        ProductInfo save = repository.save(productInfo);
        Assert.assertNotNull(save);
    }

    @Test
    public void findByProductStatus() throws Exception {
        List<ProductInfo> byProductStatus = repository.findByProductStatus(0);
        Assert.assertNotEquals(0,byProductStatus.size());
    }

}