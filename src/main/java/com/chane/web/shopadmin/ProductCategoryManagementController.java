package com.chane.web.shopadmin;

import com.chane.dto.ProductCategoryExecution;
import com.chane.dto.Result;
import com.chane.entity.ProductCategory;
import com.chane.entity.Shop;
import com.chane.enums.ProductCategoryStateEnum;
import com.chane.exception.ProductCategoryOperationException;
import com.chane.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fucheng on 2018/5/25.
 */

@Controller
@RequestMapping("/shop")
public class ProductCategoryManagementController {

    @Autowired
    private ProductCategoryService productCategoryService;


    @RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<ProductCategory>> getProductCategoryListByShopId(
            HttpServletRequest request) {
        Shop shop = new Shop();
        shop.setShopId(1L);
        request.getSession().setAttribute("currentShop",shop);
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> list = null;
        if ((currentShop != null) && (currentShop.getShopId() > 0)) {
            list = productCategoryService.getProductCategoryList(currentShop.getShopId());
            return new Result<>(true,list);
        } else {
            ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
            return new Result<>(false,ps.getState(),ps.getStateInfo());
        }
    }

    @RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProductCategorys(
            @RequestBody List<ProductCategory> productCategoryList,
            HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Shop currentShop = (Shop) request.getSession().getAttribute(
                "currentShop");
        for (ProductCategory pc : productCategoryList) {
            pc.setShopId(currentShop.getShopId());
        }
        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                ProductCategoryExecution pe = productCategoryService
                        .batchAddProductCategory(productCategoryList);
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS
                        .getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductCategoryOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少输入一个商品类别");
        }
        return modelMap;
    }

}
