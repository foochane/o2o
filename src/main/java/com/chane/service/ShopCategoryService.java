package com.chane.service;

import com.chane.entity.ShopCategory;

import java.util.List;

/**
 * Created by fucheng on 2018/5/21.
 */
public interface ShopCategoryService {

    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
