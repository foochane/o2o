package com.chane.dao;

import com.chane.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by fucheng on 2018/5/21.
 */
public interface ShopCategoryDao {

    /**
     *
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> queryShopCategory(
            @Param("shopCategoryCondition") ShopCategory shopCategoryCondition);

    /**
     *
     * @param shopCategoryId
     * @return
     */
    ShopCategory queryShopCategoryById(long shopCategoryId);

    /**
     *
     * @param shopCategoryIdList
     * @return
     */
    List<ShopCategory> queryShopCategoryByIds(List<Long> shopCategoryIdList);

    /**
     *
     * @param shopCategory
     * @return
     */
    int insertShopCategory(ShopCategory shopCategory);

    /**
     *
     * @param shopCategory
     * @return
     */
    int updateShopCategory(ShopCategory shopCategory);

    /**
     *
     * @param shopCategoryId
     * @return
     */
    int deleteShopCategory(long shopCategoryId);

    /**
     *
     * @param shopCategoryIdList
     * @return
     */
    int batchDeleteShopCategory(List<Long> shopCategoryIdList);
}
