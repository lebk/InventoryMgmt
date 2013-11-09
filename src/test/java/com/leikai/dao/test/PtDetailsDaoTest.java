package com.leikai.dao.test;

import static org.junit.Assert.*;

import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.leikai.dao.ProductDao;
import com.leikai.dao.PtDetailsDao;
import com.leikai.dao.impl.ProductDaoImpl;
import com.leikai.dao.impl.PtDetailsDaoImpl;
import com.leikai.po.Ptdetails;

/**
 * Author: lebk.lei@gmail.com Date: 2013-11-7
 */
public class PtDetailsDaoTest
{
  static Logger logger = Logger.getLogger(PtDetailsDaoTest.class);

  PtDetailsDao pdd;
  Integer opUserId = 1;
  Integer pNum = TestUtil.getPNum();
  Integer btId = 1;
  Integer poId = TestUtil.getPoId();

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
    pdd = new PtDetailsDaoImpl();

  }

  @After
  public void tearDown() throws Exception
  {
  }

  @Test
  public void testAddPtDetail()
  {
    int n1 = pdd.getAllPtDetails().size();
    logger.info("Before calling, the size is: " + n1);
    Boolean status = pdd.addPtDetail(poId, btId, pNum, opUserId);
    Assert.assertTrue("Expect add successfully, and return a non null value", status == true);
    int n2 = pdd.getAllPtDetails().size();
    logger.info("after calling, the size is: " + n2);
    Assert.assertTrue("Expect a newly ptdetails record in the table", n2 - n1 == 1);
  }

  @Test
  public void testDeletePtDetail()
  {
    // Boolean status = pdd.addPtDetail(poId, btId, pNum, opUserId);
    //
    // Assert.assertTrue("Should delete successfully", status);
  }

  @Test
  public void testGetAllPtDetails()
  {
    // fail("Not yet implemented");
  }

  @Test
  public void testDeletePtDetialByPoId()
  {
    logger.info("testDeletePtDetialByPoId, The poId is:" + poId);
    boolean status = pdd.deletePtDetialByPoId(poId);
    Assert.assertTrue("Should delete successfully", status);
    List<Ptdetails> pdl = pdd.getAllPtDetailsbyPoId(poId);
    logger.info("There should be no Ptdetails record for poId:" + poId + " after delete");
    Assert.assertTrue("expect 0 after detail", pdl.size() == 0);
  }
}
