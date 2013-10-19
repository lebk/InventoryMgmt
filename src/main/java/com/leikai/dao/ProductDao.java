package com.leikai.dao;

import java.util.List;

import com.leikai.po.Product;
import com.leikai.po.Producttype;

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

  /**
   * This method is used to retrieve all the products
   */

  public List<Product> getProductList();

  /**
   * @param pName
   * @param version
   * @param key
   * @param baseLocation
   * @param user
   * @param prodType
   * @param supportedOsList
   * @return
   */
  public boolean addProduct(String pName, String version, String key, String baseLocation, String prodType, List<String> supportedOsList, String addUser);


  public Integer getIdByProdName(String pName);

  public String getNameByProductId(Integer poId);

  public Product getProductByPoId(Integer poId);

  public boolean updateProductName(String oldName, String newName);

  public Product getProductByProdTypeAndVersion(String ptType, String version);

  public List<Producttype> getSupportedProductType();


  public boolean isProductExisted(String pName);

  public boolean isProductExisted(String prodType, String version);

  public String getProdTypebyProdTypeId(Integer prodTypeId);

}
