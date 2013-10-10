package com.leikai.services.impl;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.leikai.dao.ProductDao;
import com.leikai.dao.impl.ProductDaoImpl;
import com.leikai.enumType.ProductEnumType;
import com.leikai.po.Product;
import com.leikai.po.Producttype;
import com.leikai.services.LocationService;
import com.leikai.services.ProductService;
import com.leikai.services.UserService;

public class ProductServiceImpl implements ProductService
{
  ProductDao pd = new ProductDaoImpl();
  UserService us = new UserServiceImpl();
  LocationService ls = new LocationServiceImpl();
  static Logger logger = Logger.getLogger(ProductServiceImpl.class);

  public List<Product> getProductList()
  {
    return pd.getProductList();
  }

  public boolean addProduct(String pName, String version, String key, String prodType, List<String> supportedOsList, String uploadUser)
  {
    // TODO, upload the file to target location //baseLocation + /prodType
    // +/version + pName
    if (pName == null || version == null || prodType == null)
    {
      logger.error("pName:" + pName + ",prodType:" + prodType + ",version:" + version + "could not be null");
      return false;
    }
    String bpl = ls.getBaseProductLocation();
    logger.info("base product location is: " + bpl);
    return pd.addProduct(pName, version, key, bpl, prodType, supportedOsList, uploadUser);
  }

  public boolean removeProduct(String pName, String version, String opUser)
  {
    if (!us.isUserAdmin(opUser))
    {
      logger.error("only admin user can remove the product");
      return false;
    }
    return pd.removeProduct(pName, version);

  }

  public List<Product> getProductListfilterByOsId(Integer osId)
  {
    return pd.getProductListfilterByOsId(osId);
  }

  public Integer getIdByProdName(String pName)
  {
    return pd.getIdByProdName(pName);
  }

  public String getNameByProductId(Integer poId)
  {
    if (poId == null)
    {
      logger.error("The quried product id should not be null, return null");
      return null;
    }
    return pd.getNameByProductId(poId);
  }

  public Product getProductByPoId(Integer poId)
  {
    if (poId == null)
    {
      logger.error("The quried product id should not be null, return null");
      return null;
    }
    return pd.getProductByPoId(poId);
  }

  public boolean updateProductName(String oldName, String newName)
  {
    // TODO Auto-generated method stub
    if (newName == null)
    {
      logger.info("the new product name can not be null");
      return false;
    }

    return pd.updateProductName(oldName, newName);
  }

  public Product getProductByProdTypeAndVersion(String ptType, String version)
  {

    if (ptType == null || version == null)
    {
      logger.error("the ptType or version is null return: ptType: " + ptType + ", version: " + version);
      return null;
    }
    return pd.getProductByProdTypeAndVersion(ptType, version);
  }

  public List<String> getSupportedProductType()
  {

    List<String> ptNameList = new ArrayList<String>();
    List<Producttype> ptl = pd.getSupportedProductType();

    for (Producttype pt : ptl)
    {
      logger.info("the product type name is: " + pt.getName());
      ptNameList.add(pt.getName());
    }

    return ptNameList;

  }

  public boolean isProductSupportedToInstallOnOs(Integer prodId, Integer osId)
  {

    if (prodId == null || osId == null)
    {
      logger.info("the product id or Os id should not be null: prodId=" + prodId + ", osId=" + osId);
      return false;
    }

    return pd.isProductSupportedToInstallOnOs(prodId, osId);
  }

  public boolean isProductExisted(String pName)
  {
    // Temp fix, that is while the pass in prodType or version is null,
    // return
    // true

    if (pName == null)
    {
      logger.error("'the name is null, return true");
      return true;
    }
    return pd.isProductExisted(pName);
  }

  public boolean isProductExisted(String prodType, String version)
  {
    // Temp fix, that is while the pass in prodType or version is null,
    // return
    // true
    if (prodType == null || version == null)
    {
      logger.error("The prodType:" + prodType + " or version:" + version + " is null, return false");

      return true;
    }
    return pd.isProductExisted(prodType, version);
  }

  public String getProdTypebyProdTypeId(Integer prodTypeId)
  {
    if (prodTypeId == null)
    {
      logger.error("the quried type id is null");
      return null;
    }
    return pd.getProdTypebyProdTypeId(prodTypeId);
  }

  public boolean isSupportActivation(String prodName)
  {
    Integer id;
    String prodtype;
    ProductService ps = new ProductServiceImpl();
    id = ps.getIdByProdName(prodName);
    Integer ptId = ps.getProductByPoId(id).getPtId();
    prodtype = ps.getProdTypebyProdTypeId(ptId);
    if (prodtype.equals(ProductEnumType.n360) || prodtype.equals(ProductEnumType.nis2012) || prodtype.equals(ProductEnumType.nis2013))
    {
      return true;
    } else
      return false;
  }
}
