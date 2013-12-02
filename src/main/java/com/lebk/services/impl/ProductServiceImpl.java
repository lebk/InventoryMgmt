package com.lebk.services.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.lebk.dao.ProductDao;
import com.lebk.dao.impl.ProductDaoImpl;
import com.lebk.enumType.BusinessEnumType;
import com.lebk.enumType.UserEnumType;
import com.lebk.po.Product;
import com.lebk.po.Pttype;
import com.lebk.services.ProductColorService;
import com.lebk.services.ProductService;
import com.lebk.services.ProductSizeService;
import com.lebk.services.ProductTypeService;
import com.lebk.services.UserService;

public class ProductServiceImpl implements ProductService
{
  ProductDao pd = new ProductDaoImpl();
  UserService us = new UserServiceImpl();
  ProductTypeService pts = new ProductTypeServiceImpl();
  ProductColorService pcs = new ProductColorServiceImpl();
  ProductSizeService pss = new ProductSizeServiceImpl();
  static Logger logger = Logger.getLogger(ProductServiceImpl.class);

  public boolean updateProduct(String pName, String ptType, String ptColor, String ptSize, Integer pNum, String businessType, String opUser)
  {

    if (pNum <= 0)
    {
      logger.info("The product number should greater than 0,but:" + pNum);
      return false;
    }
    Integer ptTypeId = pts.getIdByPtType(ptType);
    if (ptTypeId == null)
    {
      logger.info("Fail to get the product type id by:" + ptType);
      return false;
    }
    Integer ptColorId = pcs.getIdByPtColor(ptColor);
    if (ptColorId == null)
    {
      logger.info("Fail to get the product color id by:" + ptColor);
      return false;
    }
    Integer ptSizeId = pss.getIdByPtSize(ptSize);
    if (ptSizeId == null)
    {
      logger.info("Fail to get the product size id by:" + ptSize);
      return false;
    }
    Integer btId = BusinessEnumType.getIdByBusinessType(businessType);
    if (btId == null)
    {
      logger.info("Fail to get the businessType id by:" + businessType);
      return false;
    }
    Integer opUserId = us.getUserIdByUsername(opUser);
    if (opUserId == null)
    {
      logger.info("Fail to get the user id by:" + opUser);
      return false;
    }
    logger.info("the product is:" + pName);
    logger.info("the type id for:" + ptType + " is:" + ptTypeId);
    logger.info("the color id for:" + ptColor + " is:" + ptColorId);
    logger.info("the size id for:" + ptSize + " is:" + ptSizeId);
    logger.info("the business type id for:" + businessType + " is:" + btId);
    logger.info("the user id for:" + ptType + " is:" + opUserId);
    logger.info("the product number is:" + pNum);
    if (pName == null || pName.length() == 0)
    {
      pName = this.constructProductName(ptType, ptColor, ptSize);
    }

    return pd.updateProduct(pName, ptTypeId, ptColorId, ptSizeId, pNum, btId, opUserId);
  }

  public boolean updateProduct(String ptType, String ptColor, String ptSize, Integer pNum, String businessType, String opUser)
  {
    String pName = this.constructProductName(ptType, ptColor, ptSize);
    return this.updateProduct(pName, ptType, ptColor, ptSize, pNum, businessType, opUser);
  }

  public boolean cleanUpAll(String opUser)
  {
    Integer id = us.getUserIdByUsername(opUser);
    logger.info("The id for user:" + opUser + " is:" + id);
    Integer userTypeId = us.getUserByUserId(id).getType();
    if (!UserEnumType.getUsertypeById(userTypeId).equals(UserEnumType.admin))
    {
      logger.error("Only admin user can call this method, deleteAllProduct()");
      return false;
    }
    return pd.cleanUpAll();
  }

  public List<Product> getAllProductList()
  {
    return pd.getProductList();
  }

  private String constructProductName(String ptType, String ptColor, String ptSize)
  {
    String pName = ptType + "-" + ptColor + "-" + ptSize;
    return pName;
  }

}
