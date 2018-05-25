package com.chane.service.Impl;

import com.chane.dao.ProductCategoryDao;
import com.chane.dto.ProductCategoryExecution;
import com.chane.entity.ProductCategory;
import com.chane.enums.ProductCategoryStateEnum;
import com.chane.exception.ProductCategoryOperationException;
import com.chane.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by fucheng on 2018/5/25.
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(
            List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                int effectedNum = productCategoryDao
                        .batchInsertProductCategory(productCategoryList);
                if (effectedNum <= 0) {
                    throw new ProductCategoryOperationException("店铺类别创建失败");
                } else {

                    return new ProductCategoryExecution(
                            ProductCategoryStateEnum.SUCCESS);
                }

            } catch (Exception e) {
                throw new ProductCategoryOperationException("batchAddProductCategory error: "
                        + e.getMessage());
            }
        } else {
            return new ProductCategoryExecution(
                    ProductCategoryStateEnum.INNER_ERROR);
        }

    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(
            long productCategoryId, long shopId) throws ProductCategoryOperationException {
        //TODO 将此类别下的商品里的类别id置为空
//        try {
//            int effectedNum = productDao
//                    .updateProductCategoryToNull(productCategoryId);
//            if (effectedNum < 0) {
//                throw new ProductCategoryOperationException("商品类别更新失败");
//            }
//        } catch (Exception e) {
//            throw new ProductCategoryOperationException("deleteProductCategory error: "
//                    + e.getMessage());
//        }
        try {
            int effectedNum = productCategoryDao.deleteProductCategory(
                    productCategoryId, shopId);
            if (effectedNum <= 0) {
                throw new ProductCategoryOperationException("店铺类别删除失败");
            } else {
                return new ProductCategoryExecution(
                        ProductCategoryStateEnum.SUCCESS);
            }

        } catch (Exception e) {
            throw new ProductCategoryOperationException("deleteProductCategory error: "
                    + e.getMessage());
        }
    }
}
