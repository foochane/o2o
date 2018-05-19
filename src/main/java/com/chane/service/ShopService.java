package com.chane.service;

import com.chane.dto.ShopExecution;
import com.chane.entity.Shop;

import java.io.File;

/**
 * Created by fucheng on 2018/5/19.
 */
public interface ShopService {

    ShopExecution addShop(Shop shop,File shopImg);
}
