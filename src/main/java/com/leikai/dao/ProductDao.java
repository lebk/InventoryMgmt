package com.leikai.dao;

import java.util.List;

import com.leikai.po.Product;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 * 
 * 2013-10-9
 * 
 */

public interface ProductDao {

	public List<Product> getProductList();

	public boolean addProduct(String pName, String pType, String pColor,
			String pSize, Integer pNum);

	public boolean reduceProduct(Integer poId, Integer pNum);

	public Integer getIdByProdName(String pName);

	public String getNameByProductId(Integer poId);

	public Product getProductByPoId(Integer poId);

	public boolean updateProductName(String oldName, String newName);

	public boolean isProductExisted(String pName);

	public String getProdTypebyProdTypeId(Integer prodTypeId);

}
