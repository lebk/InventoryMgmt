package com.lebk.services.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.lebk.dao.PtTypeDao;
import com.lebk.dao.impl.PtTypeDaoImpl;
import com.lebk.po.Pttype;
import com.lebk.services.ProductTypeService;
import com.lebk.services.UserService;

public class ProductTypeServiceImpl implements ProductTypeService
{

  static Logger logger = Logger.getLogger(ProductTypeServiceImpl.class);

  PtTypeDao ptd = new PtTypeDaoImpl();
  UserService us = new UserServiceImpl();

  public boolean addPtType(String ptType, String opUser)
  {
    Integer opUserId = us.getUserIdByUsername(opUser);
    logger.info("The id for user:" + opUser + " is:" + opUserId);
    return ptd.addPtType(ptType, opUserId);
  }

  public boolean deletePtType(String ptType, String opUser)
  {
    // TODO Auto-generated method stub
    return ptd.deletePtType(ptType);
  }

  public List<Pttype> getAllPtType()
  {
    return ptd.getAllPtType();
  }

  public Integer getIdByPtType(String pttypeName)
  {
    return ptd.getIdByPtType(pttypeName);
  }

  public boolean deletePtType(Integer ptTypeId, String opUser)
  {
    String ptType = ptd.getNameByPtTypeId(ptTypeId);
    logger.info("The pt type name of ptTypeId:" + ptTypeId + " is:" + ptType);
    return ptd.deletePtType(ptType);
  }

}
