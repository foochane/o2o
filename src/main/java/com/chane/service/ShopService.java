package com.chane.service;

import com.chane.dto.ShopExecution;
import com.chane.entity.Shop;
import com.chane.exception.ShopOperationException;

import java.io.InputStream;

/**
 * Created by fucheng on 2018/5/19.
 */
public interface ShopService {

    ShopExecution addShop(Shop shop,InputStream shopImgInputStream,String fileName) throws ShopOperationException;
}
