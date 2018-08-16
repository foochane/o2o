package com.chane.service;


import com.chane.dto.ImageHolder;
import com.chane.dto.ProductExecution;
import com.chane.entity.Product;
import com.chane.exception.ProductOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface ProductService {
	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

	Product getProductById(long productId);

	ProductExecution addProduct(Product product, ImageHolder thumbnail,
								List<ImageHolder> productImgList)
			throws ProductOperationException;

	ProductExecution modifyProduct(Product product, CommonsMultipartFile thumbnail,
                                   List<CommonsMultipartFile> productImgs) throws ProductOperationException;
}
