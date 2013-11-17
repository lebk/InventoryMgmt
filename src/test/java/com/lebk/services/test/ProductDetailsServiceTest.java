package com.lebk.services.test;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.lebk.po.Ptdetails;
import com.lebk.services.ProductDetailsService;
import com.lebk.services.impl.ProductDetailsServiceImpl;
import com.lebk.services.impl.ProductServiceImpl;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-11-17
 */

public class ProductDetailsServiceTest
{

  ProductDetailsService pds = new ProductDetailsServiceImpl();
  static Logger logger = Logger.getLogger(ProductDetailsServiceTest.class);

  @Before
  public void setUp() throws Exception
  {
  }

  @After
  public void tearDown() throws Exception
  {
  }

  @Test
  public void testGetProductDetailsByProductId()
  {
    Integer poId = 3;
    List<Ptdetails> pdl = pds.getProductDetailsByProductId(poId);

    logger.info("There are:" + pdl.size() + " record(s) existed for poId:" + poId);
    for (Ptdetails pd : pdl)
    {
      logger.info("Thehe product detail is:" + pd);
    }

    Assert.assertTrue("prodluctDetail shoud greater than 0", pdl.size() > 0);
  }

  @Test
  public void testGetAllProductDetails()
  {
    List<Ptdetails> pdl = pds.getAllProductDetails();

    logger.info("There are:" + pdl.size() + " record(s) existed");
    for (Ptdetails pd : pdl)
    {
      logger.info("Thehe product detail is:" + pd);
    }

    Assert.assertTrue("prodluctDetail shoud greater than 0", pdl.size() > 0);
  }

}
