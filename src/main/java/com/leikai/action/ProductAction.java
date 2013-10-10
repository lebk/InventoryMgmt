package com.leikai.action;

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

import com.leikai.po.Product;
import com.leikai.services.ProductService;
import com.leikai.services.impl.ProductServiceImpl;
import com.opensymphony.xwork2.ActionSupport;

public class ProductAction extends ActionSupport
{
  static Logger logger = Logger.getLogger(ProductAction.class);
  ProductService ps = new ProductServiceImpl();

  private List<Product> productList;
  private List<String> productName;
  private String yourProductName;

  private String newProductName;
  private String oldProductName;

  /*
   * All User can add get the available product list.
   */

  public String getNewProductName()
  {
    return newProductName;
  }

  public void setNewProductName(String newProductName)
  {
    this.newProductName = newProductName;
  }
  
  public String getOldProductName()
  {
    return oldProductName;
  }

  public void setOldProductName(String oldProductName)
  {
    this.oldProductName = oldProductName;
  }
  
  public List<Product> getProductListFromDB()

  {
    return ps.getProductList();
  }

  public boolean addProduct(String pName, String version, String key, String productType, List<String> supportedOsList, String opUser)
  {
    return ps.addProduct(pName, version, key, productType, supportedOsList, opUser);
  }

  public boolean removeProduct(String pName, String uName)
  {
    return true;
  }

  public boolean updateProductName(String oldName, String newName)
  {
    return true;
  }

  public boolean updateProductKey(String oldKey, String newKey)
  {
    return true;
  }

  public boolean updateProductVersion(String pName)
  {
    return true;
  }

  public List<Product> getProductListfilterByOs(String osName)
  {
    return null;
  }

  public List<Product> getProductList()
  {
    return productList;
  }

  public void setProductList(List<Product> productList)
  {
    this.productList = productList;
  }

  public List<String> getProductName()
  {
    return productName;
  }

  public void setProductName(List<String> productName)
  {
    this.productName = productName;
  }

  public String getYourProductName()
  {
    return yourProductName;
  }

  public void setYourProductName(String yourProductName)
  {
    this.yourProductName = yourProductName;
  }

  public String getDefaultProductName()
  {
    return "Be Confidence in a connected world";
  }

  public ProductAction()
  {
    productList = new ArrayList<Product>();
    productList = getProductListFromDB();

  }

  public String updateProductName()
  {
    if (oldProductName != null && oldProductName.length() > 0)
    {
      if (newProductName != null && newProductName.length() > 0)
      {
        logger.info("old product Name is: " + oldProductName);
        logger.info("updated new product Name is: " + newProductName);
        ps.updateProductName(oldProductName, newProductName);
        return "success";
      }
      else
        return "error";
    } else
      return "error";
  }

  @Override
public String execute()
  {
    return SUCCESS;
  }

  public String display()
  {
    return NONE;
  }
}
