package com.chane.service;

import com.chane.dto.ProductCategoryExecution;
import com.chane.entity.ProductCategory;
import com.chane.exception.ProductCategoryOperationException;

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

    /**
     *
     * @param productCategoryList
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution batchAddProductCategory(
            List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;


    /**
     *将此类别下的商品里的类别id置为空，再删除商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId,
                                                   long shopId) throws ProductCategoryOperationException;
}
