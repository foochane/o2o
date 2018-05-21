package com.chane.service.Impl;

import com.chane.dao.ShopCategoryDao;
import com.chane.entity.ShopCategory;
import com.chane.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fucheng on 2018/5/21.
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService{

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition){

        return shopCategoryDao.queryShopCategory(shopCategoryCondition);
    }


}
