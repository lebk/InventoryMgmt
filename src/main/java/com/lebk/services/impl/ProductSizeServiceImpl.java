package com.lebk.services.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.lebk.dao.PtSizeDao;
import com.lebk.dao.PtTypeDao;
import com.lebk.dao.impl.PtSizeDaoImpl;
import com.lebk.dao.impl.PtTypeDaoImpl;
import com.lebk.po.Ptsize;
import com.lebk.services.ProductSizeService;
import com.lebk.services.UserService;

/**
 * Author: lebk.lei@gmail.com Date: 2013-11-12
 */
public class ProductSizeServiceImpl implements ProductSizeService
{
  static Logger logger = Logger.getLogger(ProductSizeServiceImpl.class);

  UserService us = new UserServiceImpl();
  PtSizeDao psd = new PtSizeDaoImpl();

  public Boolean addPtSize(String ptSize, String opUser)
  {
    Integer opUserId = us.getUserIdByUsername(opUser);
    return psd.addPtSize(ptSize, opUserId);

  }

  public Boolean deletePtSize(String ptSize, String opUser)
  {
    Integer id = psd.getIdByPtSizeName(ptSize);
    if (this.isUsed(id))
    {
      logger.info("The product size is already is used, the product size id:" + id);
      return false;
    }

    return psd.deletePtSize(ptSize);
  }

  public List<Ptsize> getAllPtSize()
  {
    return psd.getAllPtSize();
  }

  public Integer getIdByPtSize(String ptsizeName)
  {
    return psd.getIdByPtSizeName(ptsizeName);
  }

  public Boolean isUsed(Integer ptSizeId)
  {
    return psd.isUsed(ptSizeId);
  }

  public Boolean deletePtSize(Integer ptSizeId, String opUser)
  {
    if (this.isUsed(ptSizeId))
    {
      logger.info("The product size is already used, product size id is:" + ptSizeId);
      return false;
    }
    String ptSize = psd.getNameByPtSizeId(ptSizeId);
    logger.info("The pt size name of ptSizeId:" + ptSizeId + " is:" + ptSize);
    return psd.deletePtSize(ptSize);
  }
}
