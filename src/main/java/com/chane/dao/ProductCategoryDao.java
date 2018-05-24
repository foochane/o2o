package com.chane.dao;

import com.chane.entity.ProductCategory;

import java.util.List;


public interface ProductCategoryDao {
	/**
	 * 通过shop_id查询店铺商品类别
	 * 
	 * @param shopId
	 * @return List<ProductCategory>
	 */
	List<ProductCategory> queryProductCategoryList(long shopId);


}
