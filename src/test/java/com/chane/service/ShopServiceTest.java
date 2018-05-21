package com.chane.service;

import com.chane.BaseTest;
import com.chane.dto.ShopExecution;
import com.chane.entity.Area;
import com.chane.entity.Shop;
import com.chane.entity.ShopCategory;
import com.chane.enums.ShopStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by fucheng on 2018/5/19.
 */
public class ShopServiceTest extends BaseTest{

    @Autowired
    private ShopService shopService;

	@Test
	public void testAddShop() throws Exception {
		Shop shop = new Shop();
		Area area = new Area();
        area.setAreaId(1L);
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(1L);
        //shop.setOwnerId(1L);
        shop.setShopName("mytest1");
		shop.setShopDesc("mytest1");
		shop.setShopAddr("testaddr1");
		shop.setPhone("13810524526");
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(0);
		shop.setAdvice("审核中");
		shop.setArea(area);
		shop.setShopCategory(sc);
		File shopImg = new File("D:\\code\\springboot\\o2o\\image\\watermark\\xiaoxiang.jpg");
		InputStream is = new FileInputStream(shopImg);
		ShopExecution se = shopService.addShop(shop,is,shopImg.getName());
		assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
	}
}
