package com.chane.service;

import com.chane.dto.ImageHolder;
import com.chane.dto.ShopExecution;
import com.chane.entity.Shop;
import com.chane.exception.ShopOperationException;

/**
 * Created by fucheng on 2018/5/19.
 */
public interface ShopService {


    /**
     * 根据shopCondition获取分页店铺信息
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);

    /**
     * 通过店铺id获取店铺信息
     * @param shopID
     * @return
     */
    Shop getByShopId(long shopID);


    /**
     * 更新店铺信息
     * @param shop
     * @return
     * @throws RuntimeException
     */
    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws RuntimeException;
    /**
     * 注册店铺详细 包括图片处理
     * @param shop
     * @return
     * @throws ShopOperationException
     */
    ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;
}
