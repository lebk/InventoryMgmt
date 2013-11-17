package com.lebk.services.impl;

import java.util.List;

import com.lebk.dao.PtDetailsDao;
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

  PtDetailsDao pdd = new PtDetailsDaoImpl();

  public List<Ptdetails> getProductDetailsByProductId(Integer poId)
  {

    return pdd.getAllPtDetailsbyPoId(poId);
  }

  public List<Ptdetails> getAllProductDetails()
  {
    return pdd.getAllPtDetails();
  }
}
