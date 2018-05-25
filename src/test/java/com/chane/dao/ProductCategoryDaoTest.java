package com.chane.dao;

import com.chane.BaseTest;
import com.chane.entity.ProductCategory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

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

    @Test
    public void testAInsertProductCategory() throws Exception {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("商品类别1");
        productCategory.setPriority(1);
        productCategory.setCreateTime(new Date());
        productCategory.setShopId(1L);
        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("商品类别2");
        productCategory2.setPriority(2);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(1L);
        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
        productCategoryList.add(productCategory);
        productCategoryList.add(productCategory2);
        int effectedNum = productCategoryDao
                .batchInsertProductCategory(productCategoryList);
        assertEquals(2, effectedNum);
    }

}
