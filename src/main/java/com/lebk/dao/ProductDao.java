package com.lebk.dao;

import java.util.List;

import com.lebk.po.Product;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 * 
 * 2013-10-9
 * 
 */

public interface ProductDao
{

  public List<Product> getProductList();

  public boolean updateProduct(String pName, Integer ptTypeId, Integer ptColorId, Integer ptSizeId, Integer pNum, Integer btId, Integer opUserId);

  public boolean removeProduct(Integer id);

  public Integer getIdByProdName(String pName);

  public String getNameByProductId(Integer id);

  public Product getProductById(Integer id);

  public boolean updateProductName(String oldName, String newName);

  public boolean isProductExisted(String pName);

  public String getProdTypebyProdTypeId(Integer prodTypeId);

  public boolean cleanUpAll();
  
  public List<Product> searchProduct(Integer ptTypeId, Integer ptColorId, Integer ptSizeId);
}
