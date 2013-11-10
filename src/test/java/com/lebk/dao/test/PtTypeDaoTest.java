package com.lebk.dao.test;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.lebk.dao.PtTypeDao;
import com.lebk.dao.impl.PtTypeDaoImpl;
import com.lebk.po.Ptcolor;
import com.lebk.po.Pttype;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-10-19
 */

public class PtTypeDaoTest
{

  static Logger logger = Logger.getLogger(PtTypeDaoTest.class);
  PtTypeDao ptd;
  Integer opUserId=TestUtil.getOpUserId();
  String ptTypeName = TestUtil.getPtTypeName();

  @BeforeClass
  public static void setUpBeforeClass() throws Exception
  {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception
  {
  }

  @Before
  public void setUp() throws Exception
  {
    ptd = new PtTypeDaoImpl();

  }

  @After
  public void tearDown() throws Exception
  {
    ptd = null;

  }

  @Test
  public void testAddPtType()
  {// delete first, then added.
    ptd.deletePtType(ptTypeName);
    Boolean status = ptd.addPtType(ptTypeName, opUserId);
    Assert.assertTrue("expect return true", status);
    status = ptd.isPtTypeExisted(ptTypeName);
    Assert.assertTrue("expect return true, as has been added before", status);

  }

  @Test
  public void testDeletePtType()
  {
    ptd.addPtType(ptTypeName, opUserId);
    Boolean status = ptd.deletePtType(ptTypeName);
    Assert.assertTrue("delete product type should be successfully", status);
    status = ptd.deletePtType(ptTypeName);
    Assert.assertTrue("delete product type should return false, as it has been deleted before", status == false);
  }

  @Test
  public void testGetAllPtType()
  {
    List ptdl = ptd.getAllPtType();
    Assert.assertTrue("We have 3 types of product currently", ptdl.size() == 3);
    for (Iterator it = ptdl.iterator(); it.hasNext();)
    {
      logger.info(it.next());
    }
  }

  @Test
  public void testGetIdByPtType()
  {
    ptd.addPtType(ptTypeName, opUserId);
    Integer id = ptd.getIdByPtType(ptTypeName);
    logger.info("The id is:" + id);
    Assert.assertTrue("The id should be greater than 0", id > 0);
  }

  @Test
  public void testGetNameByPtTypeId()
  {
    ptd.addPtType(ptTypeName, opUserId);
    Integer id = ptd.getIdByPtType(ptTypeName);
    String name = ptd.getNameByPtTypeId(id);
    logger.info("The name is:" + name);
    Assert.assertTrue("The name should not be null", name != null);
    ptd.deletePtType(ptTypeName);
    name = ptd.getNameByPtTypeId(id);
    Assert.assertTrue("The name should be null after delete", name == null);

  }

  @Test
  public void testGetPtTypeByPtTypeId()
  {
    ptd.addPtType(ptTypeName, opUserId);
    Integer id = ptd.getIdByPtType(ptTypeName);
    logger.info("The product type id is:" + id);

    Pttype pt = ptd.getPtTypeByPtTypeId(id);
    logger.info("The pt type name is:" + pt.getType());
    Assert.assertTrue("The pt type name should not be null", pt != null);

  }

  @Test
  public void testIsPtTypeExisted()
  {
    ptd.addPtType(ptTypeName, opUserId);
    Boolean status = ptd.isPtTypeExisted(ptTypeName);
    Assert.assertTrue("The product type should be existed as I just added it", status);
    ptd.deletePtType(ptTypeName);
    status = ptd.isPtTypeExisted(ptTypeName);
    Assert.assertTrue("The product should not be existed, as I just delete it", status == false);
  }

}
