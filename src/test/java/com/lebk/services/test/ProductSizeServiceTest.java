package com.lebk.services.test;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.lebk.dao.test.ProductDaoTest;
import com.lebk.po.Ptsize;
import com.lebk.services.ProductSizeService;
import com.lebk.services.impl.ProductSizeServiceImpl;

/**
 * Author: lebk.lei@gmail.com Date: 2013-11-12
 */
public class ProductSizeServiceTest
{
  static Logger logger = Logger.getLogger(ProductSizeServiceTest.class);

  ProductSizeService pss = new ProductSizeServiceImpl();

  @AfterClass
  public static void tearDownAfterClass() throws Exception
  {
  }

  @Before
  public void setUp() throws Exception
  {
  }

  @After
  public void tearDown() throws Exception
  {
  }

  @Test
  public void testAddPtSize()
  {
   // fail("Not yet implemented");
  }

  @Test
  public void testDeletePtSize()
  {
    //fail("Not yet implemented");
  }

  @Test
  public void testGetAllPtSize()
  {
    List<Ptsize> psl = pss.getAllPtSize();
    for (Iterator it = psl.iterator(); it.hasNext();)
    {
      Ptsize ps = (Ptsize) it.next();
      logger.info("The product size is:" + ps.getSize());
    }

    Assert.assertTrue("expect the size greater than 0", psl.size() > 0);

  }

  @Test
  public void testIsUsed()
  {
    Integer ptSizeId = 4;
    Boolean status = pss.isUsed(ptSizeId);
    Assert.assertTrue("expect true", status == true);

    status = pss.isUsed(5);
    Assert.assertTrue("expect false", status == false);

    status = pss.isUsed(1);
    Assert.assertTrue("expect false", status == false);

  }
  
}
