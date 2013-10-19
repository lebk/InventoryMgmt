package com.leikai.services;

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
public interface ProductService
{
  public List<Product> getProductList();

  /**
   * 
   * @param pName
   *          , the product name which like: Sep64.msi
   * @param version
   *          : the version of the product
   * @param key
   *          : the product key, this is not a must
   * @param uploadUser
   *          : the user who upload this product
   * @param prodType
   *          : currently, we just support NIS and SEP
   * @param supportedOsList
   *          , the platform the product will be supported to install.
   * @return true (if add successfully, otherwise, return false.
   * 
   *         This method is used to add a new product which will be used to
   *         install.
   */
  public boolean addProduct(String pName, String version, String key, String prodType, List<String> supportedOsList, String uploadUser);

  /**
   * @param pName
   *          : product Name
   * @param version
   *          : product version
   * @param opUser
   *          : the user tries to remove the product
   * @return
   * 
   *         This method is used to remove the product, the provided parameter
   *         is product name and version, If the user initializes the remove
   *         operation is not a admin user, then it will reject (return false).
   */

  /**
   * 
   * @param pName
   * @return
   */
  public Integer getIdByProdName(String pName);

  /**
   * 
   * @param ptType
   * @param version
   * @return
   * 
   *         Get product by the specific type and version
   */
  public Product getProductByProdTypeAndVersion(String ptType, String version);


  /**
   * 
   * @param poId
   * @return
   */
  public Product getProductByPoId(Integer poId);

  /**
   * 
   * @param poId
   * @return
   */
  public boolean updateProductName(String oldName, String newName);

  /**
   * 
   * @return
   * 
   *         This method is used to retrieve the supported product type (which
   *         is allowed to install by the VMFactory
   */
  public List<String> getSupportedProductType();


  /**
   * 
   * @param pName
   * @return
   * 
   * 
   *         This method is used to check whether the product is already been
   *         uploaded before (by product name, as product name is unique,
   * 
   */
  public boolean isProductExisted(String pName);

  /**
   * 
   * @param prodType
   * @param version
   * @return
   * 
   * 
   *         This method is used to determine whether the product has been
   *         uploaded before (by product type and version) as the combination of
   *         productType and version is unique
   */
  public boolean isProductExisted(String prodType, String version);

  /**
   * 
   * @param productTypeId
   * @return
   * 
   */
  public String getProdTypebyProdTypeId(Integer productTypeId);



}
