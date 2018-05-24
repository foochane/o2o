package com.chane.dao;

import com.chane.BaseTest;
import com.chane.entity.ProductCategory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by fucheng on 2018/5/25.
 */
public class ProductCategoryDaoTest extends BaseTest{

    @Autowired
    private  ProductCategoryDao productCategoryDao;

    Logger logger = LoggerFactory.getLogger(ProductCategoryDaoTest.class);

    @Test
    public void testQueryByShopId()throws  Exception{
        long shopId = 1;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
        logger.info("自定义类别数：{}",productCategoryList.size());
    }
}
