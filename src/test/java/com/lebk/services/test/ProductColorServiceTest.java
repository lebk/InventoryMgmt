package com.lebk.services.test;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lebk.po.Ptcolor;
import com.lebk.services.ProductColorService;
import com.lebk.services.impl.ProductColorServiceImpl;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-11-12
 */

public class ProductColorServiceTest
{
  static Logger logger = Logger.getLogger(ProductColorServiceTest.class);

  ProductColorService pcs = new ProductColorServiceImpl();

  @Before
  public void setUp() throws Exception
  {
  }

  @After
  public void tearDown() throws Exception
  {
  }

  @Test
  public void testAddPtColor()
  {
    // fail("Not yet implemented");
  }

  @Test
  public void testDeletePtColor()
  {
    fail("Not yet implemented");
  }

  @Test
  public void testGetAllPtColor()
  {
    List<Ptcolor> pcl = pcs.getAllPtColor();
    for (Iterator it = pcl.iterator(); it.hasNext();)
    {
      Ptcolor pt = (Ptcolor) it.next();
      logger.info("pt color is:" + pt.getColor());
    }
    Assert.assertTrue("The size should be greater than 0", pcl.size() > 0);
  }

}
