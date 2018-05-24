package com.chane.dao;

import com.chane.BaseTest;
import com.chane.entity.Area;
import com.chane.entity.PersonInfo;
import com.chane.entity.Shop;
import com.chane.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by fucheng on 2018/5/19.
 */
public class ShopDaoTest extends BaseTest{

    @Autowired
    private ShopDao shopDao;
     Logger logger = LoggerFactory.getLogger(ShopDaoTest.class);

     @Test
     public void tesstQureyShopListAndCount(){
         Shop shopCondition = new Shop();
         PersonInfo owner = new PersonInfo();
         owner.setUserId(1L);
         shopCondition.setOwner(owner);
         List<Shop> shopList = shopDao.queryShopList(shopCondition,0,5);
         int count = shopDao.queryShopCount(shopCondition);
         logger.info("店铺列表的大小:{}",shopList.size());
         logger.info("店铺总数:{}",count);

     }


    @Test
    @Ignore
    public void testQueryByShopId(){
        long shopId = 1;
        Shop shop = shopDao.queryByShopId(shopId);
//        System.out.println("areaId"+shop.getArea().getAreaId());
//        System.out.println("areaName"+shop.getArea().getAreaName());
        logger.info("areaId:{}",shop.getArea().getAreaId());
        logger.info("areaName:{}",shop.getArea().getAreaName());
    }

    @Test
    @Ignore
    public void testInsertShop(){
        Shop shop = new Shop();
        //shop.setOwnerId(1L);
        Area area = new Area();
        area.setAreaId(1L);
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(1L);
        shop.setShopName("mytest1");
        shop.setShopDesc("mytest1");
        shop.setShopAddr("testaddr1");
        shop.setPhone("13810524526");
        shop.setShopImg("test1");
        shop.setLongitude(1D);
        shop.setLatitude(1D);
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(0);
        shop.setAdvice("审核中");
        shop.setArea(area);
        shop.setShopCategory(sc);
        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1, effectedNum);
    }

    @Test
    @Ignore
    public void testDUpdateShop() {

        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopDesc("测试描述");
        shop.setAdvice("ceshi.........");
        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1, effectedNum);
    }
}
