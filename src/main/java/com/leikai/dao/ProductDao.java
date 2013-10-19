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

public interface ProductDao
{

  public List<Product> getProductList();

  public boolean addProduct(String pName, String version, String key, String baseLocation, String prodType, List<String> supportedOsList, String addUser);


  public Integer getIdByProdName(String pName);

  public String getNameByProductId(Integer poId);

  public Product getProductByPoId(Integer poId);

  public boolean updateProductName(String oldName, String newName);

  public boolean isProductExisted(String pName);

  public String getProdTypebyProdTypeId(Integer prodTypeId);

}
