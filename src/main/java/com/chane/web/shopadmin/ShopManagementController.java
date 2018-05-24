package com.chane.web.shopadmin;

import com.chane.dto.ShopExecution;
import com.chane.entity.Area;
import com.chane.entity.PersonInfo;
import com.chane.entity.Shop;
import com.chane.entity.ShopCategory;
import com.chane.enums.ShopStateEnum;
import com.chane.service.AreaService;
import com.chane.service.ShopCategoryService;
import com.chane.service.ShopService;
import com.chane.util.CodeUtil;
import com.chane.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fucheng on 2018/5/21.
 */

@Controller
@RequestMapping("/shop")
public class ShopManagementController {

    @Autowired
    private ShopService ShopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId <=0){
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if(currentShopObj == null){
                modelMap.put("redirect",true);
                modelMap.put("url","shop/shoplist");
            }else {
                Shop currentShop = (Shop)currentShopObj;
                modelMap.put("redirect",false);
                modelMap.put("shopId",currentShop.getShopId());
            }
        }else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop",currentShop);
            modelMap.put("redirect",false);
        }

        return modelMap;
    }

    @RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        PersonInfo user = new PersonInfo();
        user.setUserId(1L);
        request.getSession().setAttribute("user",user);
        user = (PersonInfo)request.getSession().getAttribute("user");
        try{
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopExecution se = shopService.getShopList(shopCondition,0,100);
            modelMap.put("shopList",se.getShopList());
            modelMap.put("user",user);
            modelMap.put("success",true);
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }

        return  modelMap;
    }

    @RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        // 1、接受并转化相应参数，包括店铺信息及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        // 使用jackson-databind-->https://github.com/FasterXML/jackson-databind
        ObjectMapper mapper = new ObjectMapper(); // create once, reuse（创建一次，可重用）
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        // 获取图片文件流
        MultipartHttpServletRequest multipartRequest = null;
        MultipartFile shopImg = null;
        MultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            multipartRequest = (MultipartHttpServletRequest) request;
            shopImg =  multipartRequest.getFile("shopImg");
        }

        // 2、修改店铺信息
        if (shop != null && shop.getShopId() != null) {
            ShopExecution se ;
            try {
                if(shopImg == null){
                    se = ShopService.modifyShop(shop, null,null);
                }else {
                    se = ShopService.modifyShop(shop, shopImg.getInputStream(),shopImg.getOriginalFilename());
                }
                if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }

            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺ID");
            return modelMap;
        }
    }



    /**
     *get shop by id
     * @param request
     * @return
     * url:http://localhost:8080/shopadmin/getshopbyid?shopId=1
     */
    @RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getShopById(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        Long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId > -1){
            try{
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop",shop);
                modelMap.put("areaList",areaList);
                modelMap.put("success",true);
            } catch (Exception e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","empty shopId");
        }
        return modelMap;
    }


    @RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        List<Area> areaList = new ArrayList<Area>();
        try {
            shopCategoryList = shopCategoryService
                    .getShopCategoryList(new ShopCategory());
            areaList = areaService.queryArea();
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        modelMap.put("shopCategoryList", shopCategoryList);
        modelMap.put("areaList", areaList);
        modelMap.put("success", true);
        return modelMap;
    }

    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        // 1、接受并转化相应参数，包括店铺信息及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        // 使用jackson-databind-->https://github.com/FasterXML/jackson-databind
        ObjectMapper mapper = new ObjectMapper(); // create once, reuse（创建一次，可重用）
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        // 获取图片文件流
        MultipartHttpServletRequest multipartRequest = null;
        MultipartFile shopImg = null;
        MultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            multipartRequest = (MultipartHttpServletRequest) request;
            shopImg =  multipartRequest.getFile("shopImg");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }

        // 2、注册店铺，尽量不要依靠前端信息
        if (shop != null && shopImg != null) {
            PersonInfo owner = (PersonInfo)request.getSession().getAttribute("user");

            shop.setOwner(owner);
            shop.setOwnerId(1L);
            ShopExecution se = null;
            try {
                se = ShopService.addShop(shop, shopImg.getInputStream(),shopImg.getOriginalFilename());
                if (se.getState() == ShopStateEnum.CHECK.getState()) {
                    modelMap.put("success", true);
                    //该用户可以操作的店铺列表
                    List<Shop> shopList = (List<Shop>)request.getSession().getAttribute("shopList");
                    if(shopList == null || shopList.size() == 0){
                        shopList = new ArrayList<>();
                    }
                    shopList.add(se.getShop());
                    request.getSession().setAttribute("shopList",shopList);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }

            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺信息");
            return modelMap;
        }
    }



    /**
     * 视频教程中的私有方法
     */
//    private static void inputStreamToFile(InputStream ins, File file) {
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(file);
//            int bytesRead = 0;
//            byte[] buffer = new byte[1024];
//            while ((bytesRead = ins.read(buffer)) != -1) {
//                fos.write(buffer, 0, bytesRead);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("调用inputStreamToFile产生异常：" + e.getMessage());
//        } finally {
//            try {
//                if (fos != null) {
//                    fos.close();
//                }
//                if (ins != null) {
//                    ins.close();
//                }
//            } catch (IOException e) {
//                throw new RuntimeException("inputStreamToFile关闭io产生异常：" + e.getMessage());
//            }
//        }
//    }

}
