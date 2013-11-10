package com.lebk.services.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.lebk.dao.ProductDao;
import com.lebk.dao.impl.ProductDaoImpl;
import com.lebk.po.Product;
import com.lebk.services.ProductService;
import com.lebk.services.UserService;

public class ProductServiceImpl implements ProductService
{
  ProductDao pd = new ProductDaoImpl();
  UserService us = new UserServiceImpl();
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
    String bpl = "";
    logger.info("base product location is: " + bpl);
    // return pd.addProduct(pName, version, key, bpl, prodType,
    // supportedOsList, uploadUser);
    return false;
  }

  public Integer getIdByProdName(String pName)
  {
    return pd.getIdByProdName(pName);
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

  public String getProdTypebyProdTypeId(Integer prodTypeId)
  {
    if (prodTypeId == null)
    {
      logger.error("the quried type id is null");
      return null;
    }
    return pd.getProdTypebyProdTypeId(prodTypeId);
  }

}
