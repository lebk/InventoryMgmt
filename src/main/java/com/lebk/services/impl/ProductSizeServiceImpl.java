package com.lebk.services.impl;

import java.util.List;

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
  UserService us = new UserServiceImpl();
  PtSizeDao psd = new PtSizeDaoImpl();

  public boolean addPtSize(String ptSize, String opUser)
  {
    Integer opUserId = us.getUserIdByUsername(opUser);
    return psd.addPtSize(ptSize, opUserId);

  }

  public boolean deletePtSize(String ptSize, String opUser)
  {
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

}
