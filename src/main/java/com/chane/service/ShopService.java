package com.chane.service;

import com.chane.dto.ShopExecution;
import com.chane.entity.Shop;
import com.chane.exception.ShopOperationException;

import java.io.InputStream;

/**
 * Created by fucheng on 2018/5/19.
 */
public interface ShopService {

    /**
     * 通过店铺id获取店铺信息
     * @param shopID
     * @return
     */
    Shop getByShopId(long shopID);


    /**
     * 更新店铺信息
     * @param shop
     * @param shopImgInputStream
     * @param fileName
     * @return
     * @throws RuntimeException
     */
    ShopExecution modifyShop(Shop shop,InputStream shopImgInputStream,String fileName) throws RuntimeException;
    /**
     * 注册店铺详细 包括图片处理
     * @param shop
     * @param shopImgInputStream
     * @param fileName
     * @return
     * @throws ShopOperationException
     */
    ShopExecution addShop(Shop shop,InputStream shopImgInputStream,String fileName) throws ShopOperationException;
}
