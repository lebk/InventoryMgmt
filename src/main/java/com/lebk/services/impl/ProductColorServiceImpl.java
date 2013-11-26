package com.lebk.services.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.lebk.dao.PtColorDao;
import com.lebk.dao.PtTypeDao;
import com.lebk.dao.impl.PtColorDaoImpl;
import com.lebk.dao.impl.PtTypeDaoImpl;
import com.lebk.po.Ptcolor;
import com.lebk.services.ProductColorService;
import com.lebk.services.UserService;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-11-12
 */

public class ProductColorServiceImpl implements ProductColorService
{
  static Logger logger = Logger.getLogger(ProductColorServiceImpl.class);

  PtColorDao pcd = new PtColorDaoImpl();
  UserService us = new UserServiceImpl();

  public boolean addPtColor(String ptColor, String opUser)
  {
    Integer opUserId = us.getUserIdByUsername(opUser);
    return pcd.addPtColor(ptColor, opUserId);
  }

  public boolean deletePtColor(String ptColor, String opUser)
  {
    Integer id = pcd.getIdByPtColorName(ptColor);
    if (this.isUsed(id))
    {
      logger.info("The product color is already is used, the product color id:" + id);
      return false;
    }

    return pcd.deletePtColor(ptColor);
  }

  public boolean deletePtColor(Integer ptColorId, String opUser)
  {
    if (this.isUsed(ptColorId))
    {
      logger.info("The product color is already used, product id is:" + ptColorId);
      return false;
    }
    String ptColor = pcd.getColorNameByPtColorId(ptColorId);
    logger.info("The pt color name of ptColorId:" + ptColorId + " is:" + ptColor);

    return pcd.deletePtColor(ptColor);
  }

  public List<Ptcolor> getAllPtColor()
  {

    return pcd.getAllPtColor();
  }

  public Integer getIdByPtColor(String ptcolorName)
  {
    return pcd.getIdByPtColorName(ptcolorName);
  }

  public Boolean isUsed(Integer ptColorId)
  {
    return pcd.isUsed(ptColorId);
  }
}
