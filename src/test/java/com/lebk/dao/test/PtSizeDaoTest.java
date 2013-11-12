package com.lebk.dao.test;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.lebk.dao.PtColorDao;
import com.lebk.dao.PtSizeDao;
import com.lebk.dao.impl.PtSizeDaoImpl;
import com.lebk.po.Ptcolor;
import com.lebk.po.Ptsize;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-10-24
 */

public class PtSizeDaoTest
{
  static Logger logger = Logger.getLogger(PtSizeDaoTest.class);
  PtSizeDao psd;
  String ptSizeName = TestUtil.getPtSizeName();

  @Before
  public void setUp() throws Exception
  {
    psd = new PtSizeDaoImpl();
  }

  @After
  public void tearDown() throws Exception
  {
    psd = null;
  }

  @Test
  public void testAddPtSize()
  {
    Boolean status = psd.deletePtSize(ptSizeName);
    status = psd.addPtSize(ptSizeName, TestUtil.getOpUserId());
    Assert.assertTrue("Add size successfully", status);
    status = psd.addPtSize(ptSizeName, TestUtil.getOpUserId());
    Assert.assertTrue("Add size should be failed as it is already existed", status == false);
  }

  @Test
  public void testDeletePtSize()
  {
    Boolean status = psd.deletePtSize(ptSizeName);
    Assert.assertTrue("delete size successfully", status);
    status = psd.deletePtSize(ptSizeName);
    Assert.assertTrue("delete size should fail as it does not existed only more", status == false);
  }

  @Test
  public void testGetIdByPtSizeName()
  {
    Integer id = psd.getIdByPtSizeName(ptSizeName);
    logger.info("The id is:" + id);
    Assert.assertTrue("The id should be greater than 0", id > 0);
  }

  @Test
  public void testGetNameByPtSizeId()
  {
    Integer id = psd.getIdByPtSizeName(ptSizeName);
    String name = psd.getNameByPtSizeId(id);
    logger.info("The name is:" + name);
    Assert.assertTrue("The name should not be null", name != null);
  }

  @Test
  public void testGetPtSizeByPtsizeId()
  {
    Integer id = psd.getIdByPtSizeName(ptSizeName);
    Ptsize ps = psd.getPtSizeByPtsizeId(id);
    logger.info("The pc size  is:" + ps.getSize());
    Assert.assertTrue("The pc size should not be null", ps != null);
  }

  @Test
  public void testIsPtSizeExisted()
  {
    psd.deletePtSize(ptSizeName);
    Boolean status = psd.isPtSizeExisted(ptSizeName);
    Assert.assertTrue("The size should not be existed after deleting", status == false);

    psd.addPtSize(ptSizeName, TestUtil.getOpUserId());
    status = psd.isPtSizeExisted(ptSizeName);
    Assert.assertTrue("The size should be existed after adding", status == true);
  }

}
