package com.chane.dao;

import com.chane.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by fucheng on 2018/5/19.
 */
public interface ShopDao {

    /**
     * 分页查询店铺,可输入的条件有：店铺名（模糊），店铺状态，店铺Id,店铺类别,区域ID
     *
     * @param shopCondition
     * @param rowIndex 从第几行开始取数据
     * @param pageSize
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
                             @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 返回queryShopList总数
     *
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);
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
