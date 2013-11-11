package com.lebk.services.test;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lebk.dao.test.TestUtil;
import com.lebk.po.Pttype;
import com.lebk.services.ProductTypeService;
import com.lebk.services.impl.ProductTypeServiceImpl;

public class PtTypeServiceTest
{
  static Logger logger = Logger.getLogger(PtTypeServiceTest.class);

  ProductTypeService pts = new ProductTypeServiceImpl();
  // String ptType=TestUtil.getRandString(8);
  String opUser = "管理员";

  @Before
  public void setUp() throws Exception
  {
  }

  @After
  public void tearDown() throws Exception
  {
  }

  @Test
  public void testAddPtType()
  {
    String type = TestUtil.getRandString(8);
    Boolean status = pts.addPtType(type, opUser);
    Assert.assertTrue("Expect added successfully", status);
    status = pts.addPtType(type, opUser);
    Assert.assertTrue("Expect added failed as it is arleady existed", status == false);

  }

  @Test
  public void testDeletePtType()
  {
    // fail("Not yet implemented");
  }

  @Test
  public void testGetAllPtType()
  {
    List<Pttype> ptl = pts.getAllPtType();
    Iterator it = ptl.iterator();
    while (it.hasNext())
    {
      Pttype pt = (Pttype) it.next();
      logger.info("The type is:" + pt);

    }

  }
}
