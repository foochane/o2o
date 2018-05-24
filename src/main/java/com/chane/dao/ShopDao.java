package com.chane.dao;

import com.chane.entity.Shop;

/**
 * Created by fucheng on 2018/5/19.
 */
public interface ShopDao {
    /**
     * 通过owner id 查询店铺
     *
     * @param shopId
     * @return shop
     */
    Shop queryByShopId(long shopId);

    /**
     * 新增店铺
     *
     * @param shop
     * @return effectedNum
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺信息
     *
     * @param shop
     * @return effectedNum
     */
    int updateShop(Shop shop);
}
