package com.chane.service.Impl;

import com.chane.dao.ShopDao;
import com.chane.dto.ImageHolder;
import com.chane.dto.ShopExecution;
import com.chane.entity.Shop;
import com.chane.enums.ShopStateEnum;
import com.chane.exception.ShopOperationException;
import com.chane.service.ShopService;
import com.chane.util.ImageUtil;
import com.chane.util.PageCalculator;
import com.chane.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by fucheng on 2018/5/19.
 */
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex,
                pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if (shopList != null) {
            se.setShopList(shopList);
            se.setCount(count);
        } else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    @Override
    public Shop getByShopId(long shopID) {
        return shopDao.queryByShopId(shopID);
    }

    @Override
    public ShopExecution modifyShop(Shop shop,  ImageHolder thumbnail) throws RuntimeException {

        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP_ID);
        } else {
            try {
                //1.判断是否需要处理图片
                if (thumbnail.getImage() != null) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg() != null) {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop,thumbnail);
                }

                //2.更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {// 创建成功
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new RuntimeException("modifyShop error: "
                        + e.getMessage());
            }
        }
    }

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop,  ImageHolder thumbnail) throws ShopOperationException {
        //空值判断
        if(shop == null ){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try{
            //给店铺信息附初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //添加店铺信息
            int effectNum = shopDao.insertShop(shop);
            if(effectNum <= 0){
                throw new ShopOperationException("店铺创建失败");
            }else {
                if(thumbnail.getImage() != null){
                    //存储图片
                    try{
                        addShopImg(shop,thumbnail);
                    }catch (Exception e){
                        throw new ShopOperationException("addShopImg error"+e.getMessage());
                    }
                    //更新店铺的图片地址
                    effectNum = shopDao.updateShop(shop);
                    if(effectNum <= 0){
                        throw new ShopOperationException("更新图片地址失败");
                    }

                }
            }

        }catch (Exception e){
            throw new ShopOperationException("addShop error"+e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    private void addShopImg(Shop shop,  ImageHolder thumbnail) {
        //获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImage(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(thumbnail,dest);
        shop.setShopImg(shopImgAddr);
    }
}
