package com.lebk.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lebk.dao.PtDetailsDao;
import com.lebk.dao.impl.ProductDaoImpl;
import com.lebk.dao.impl.PtDetailsDaoImpl;
import com.lebk.po.Ptdetails;
import com.lebk.services.ProductDetailsService;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-11-17
 */

public class ProductDetailsServiceImpl implements ProductDetailsService
{
  static Logger logger = Logger.getLogger(ProductDetailsServiceImpl.class);

  PtDetailsDao pdd = new PtDetailsDaoImpl();

  public List<Ptdetails> getProductDetailsByProductId(Integer poId)
  {

    return pdd.getAllPtDetailsbyPoId(poId);
  }

  public List<Ptdetails> getAllProductDetails()
  {
    return pdd.getAllPtDetails();
  }

  public List<Ptdetails> getProductDetailsByPoIdList(List<Integer> poIdList)
  {
    List<Ptdetails> pdl = new ArrayList<Ptdetails>();
    if (poIdList == null || poIdList.size() == 0)
    {
      logger.info("The po id list is null, return empty string");
      return pdl;
    }
    return pdd.getProductDetailsByPoIdList(poIdList);
  }
}
