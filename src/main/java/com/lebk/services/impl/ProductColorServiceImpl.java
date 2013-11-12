package com.lebk.services.impl;

import java.util.List;

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
  PtColorDao pcd = new PtColorDaoImpl();
  UserService us = new UserServiceImpl();

  public boolean addPtColor(String ptColor, String opUser)
  {
    Integer opUserId = us.getUserIdByUsername(opUser);
    return pcd.addPtColor(ptColor, opUserId);
  }

  public boolean deletePtColor(String ptColor, String opUser)
  {
    return pcd.deletePtColor(ptColor);
  }

  public List<Ptcolor> getAllPtColor()
  {

    return pcd.getAllPtColor();
  }
}
