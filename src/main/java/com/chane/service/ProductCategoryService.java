package com.chane.service;

import com.chane.entity.ProductCategory;

import java.util.List;

/**
 * Created by fucheng on 2018/5/25.
 */
public interface ProductCategoryService {

    /**
     * 查询某店铺下的所有商品类别信息
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(long shopId);
}
