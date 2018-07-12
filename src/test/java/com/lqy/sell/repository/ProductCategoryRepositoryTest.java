package com.lqy.sell.repository;

import com.lqy.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Rodriguez
 * 2018/7/12 9:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOne() {
        ProductCategory productCategory = repository.findOne(1);
        System.out.println(productCategory);
    }

    @Test
    @Transactional  // 测试的数据，不保留到数据库中
    public void saveTest() {
        /*ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("女生最爱");
        productCategory.setCategoryType(3);
        repository.save(productCategory);*/

        ProductCategory productCategory = new ProductCategory("男生最爱", 4);
        ProductCategory save = repository.save(productCategory);
        Assert.assertNotNull(save);
        // Assert.assertNotEquals(null,save);
    }

    @Test
    public void updateTest() {
        ProductCategory productCategory = repository.findOne(2);
        productCategory.setCategoryType(3);
        repository.save(productCategory);
    }

    @Test
    public void findByCategoryTypeIn() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        List<ProductCategory> categoryList = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0, categoryList.size());
    }
}