package com.chane.web.shopadmin;


import com.chane.dto.ImageHolder;
import com.chane.dto.ProductExecution;
import com.chane.entity.Product;
import com.chane.entity.ProductCategory;
import com.chane.entity.Shop;
import com.chane.enums.ProductStateEnum;
import com.chane.exception.ProductOperationException;
import com.chane.service.ProductCategoryService;
import com.chane.service.ProductService;
import com.chane.util.CodeUtil;
import com.chane.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shop")
public class ProductManagementController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;

	private static final int IMAGEMAXCOUNT = 6;

//	@RequestMapping(value = "/listproductsbyshop", method = RequestMethod.GET)
//	@ResponseBody
//	private Map<String, Object> listProductsByShop(HttpServletRequest request) {
//		Map<String, Object> modelMap = new HashMap<String, Object>();
//		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
//		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
//		Shop currentShop = (Shop) request.getSession().getAttribute(
//				"currentShop");
//		if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null)
//				&& (currentShop.getShopId() != null)) {
//			long productCategoryId = HttpServletRequestUtil.getLong(request,
//					"productCategoryId");
//			String productName = HttpServletRequestUtil.getString(request,
//					"productName");
//			Product productCondition = compactProductCondition4Search(
//					currentShop.getShopId(), productCategoryId, productName);
//			ProductExecution pe = productService.getProductList(
//					productCondition, pageIndex, pageSize);
//			modelMap.put("productList", pe.getProductList());
//			modelMap.put("count", pe.getCount());
//			modelMap.put("success", true);
//		} else {
//			modelMap.put("success", false);
//			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
//		}
//		return modelMap;
//	}
//
//	@RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
//	@ResponseBody
//	private Map<String, Object> getProductById(@RequestParam Long productId) {
//		Map<String, Object> modelMap = new HashMap<String, Object>();
//		if (productId > -1) {
//			Product product = productService.getProductById(productId);
//			List<ProductCategory> productCategoryList = productCategoryService
//					.getByShopId(product.getShop().getShopId());
//			modelMap.put("product", product);
//			modelMap.put("productCategoryList", productCategoryList);
//			modelMap.put("success", true);
//		} else {
//			modelMap.put("success", false);
//			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
//		}
//		return modelMap;
//	}
//
//	@RequestMapping(value = "/getproductcategorylistbyshopId", method = RequestMethod.GET)
//	@ResponseBody
//	private Map<String, Object> getProductCategoryListByShopId(
//			HttpServletRequest request) {
//		Map<String, Object> modelMap = new HashMap<String, Object>();
//		Shop currentShop = (Shop) request.getSession().getAttribute(
//				"currentShop");
//		if ((currentShop != null) && (currentShop.getShopId() != null)) {
//			List<ProductCategory> productCategoryList = productCategoryService
//					.getByShopId(currentShop.getShopId());
//			modelMap.put("productCategoryList", productCategoryList);
//			modelMap.put("success", true);
//		} else {
//			modelMap.put("success", false);
//			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
//		}
//		return modelMap;
//	}

	@RequestMapping(value = "/addproduct", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProduct(HttpServletRequest request) throws IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//验证码校验
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}

		//接收前端参数，包括商品、缩略图、详情图列表
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = HttpServletRequestUtil.getString(request,
				"productStr");
		MultipartHttpServletRequest multipartRequest = null;
		ImageHolder thumbnail = null;
		List<ImageHolder> productImgList = new ArrayList<>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			//若请求中存在文件流，则取出相关文件
			if (multipartResolver.isMultipart(request)) {
				multipartRequest = (MultipartHttpServletRequest) request;
				//取出文件流并够建ImageHolder对象
				CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
				thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(),thumbnailFile.getInputStream());
				for (int i = 0; i < IMAGEMAXCOUNT; i++) {
					CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest
							.getFile("productImg" + i);
					if (productImgFile != null) {
						ImageHolder productImg = new ImageHolder(thumbnailFile.getOriginalFilename(),thumbnailFile.getInputStream());
						productImgList.add(productImg);
					}else {
						break;
					}
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "上传图片不能为空");
				return modelMap;
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		try {
			product = mapper.readValue(productStr, Product.class);
		} catch (ProductOperationException e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}

		//若Product信息、缩略图以及详情图列表为非空，则开始进行商品添加
		if (product != null && thumbnail != null && productImgList.size() > 0) {
			try {
				//从session中获取当前店铺的id并赋值给product，减少对前端数据的依赖
				Shop currentShop = (Shop) request.getSession().getAttribute(
						"currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				//执行添加操作
				ProductExecution pe = productService.addProduct(product,
						thumbnail, productImgList);
				if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (ProductOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
		}
		return modelMap;
	}

	@RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyProduct(HttpServletRequest request) {
		boolean statusChange = HttpServletRequestUtil.getBoolean(request,
				"statusChange");
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = HttpServletRequestUtil.getString(request,
				"productStr");
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartFile thumbnail = null;
		List<CommonsMultipartFile> productImgs = new ArrayList<CommonsMultipartFile>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			multipartRequest = (MultipartHttpServletRequest) request;
			thumbnail = (CommonsMultipartFile) multipartRequest
					.getFile("thumbnail");
			for (int i = 0; i < IMAGEMAXCOUNT; i++) {
				CommonsMultipartFile productImg = (CommonsMultipartFile) multipartRequest
						.getFile("productImg" + i);
				if (productImg != null) {
					productImgs.add(productImg);
				}
			}
		}
		try {
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if (product != null) {
			try {
				Shop currentShop = (Shop) request.getSession().getAttribute(
						"currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				ProductExecution pe = productService.modifyProduct(product,
						thumbnail, productImgs);
				if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
		}
		return modelMap;
	}

	private Product compactProductCondition4Search(long shopId,
			long productCategoryId, String productName) {
		Product productCondition = new Product();
		Shop shop = new Shop();
		shop.setShopId(shopId);
		productCondition.setShop(shop);
		if (productCategoryId != -1L) {
			ProductCategory productCategory = new ProductCategory();
			productCategory.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(productCategory);
		}
		if (productName != null) {
			productCondition.setProductName(productName);
		}
		return productCondition;
	}
}
