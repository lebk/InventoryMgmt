package com.leikai.test;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.leikai.util.OVFOperation;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class OVFOperationTest
{
  static Logger logger = Logger.getLogger(OVFOperationTest.class);

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
  }

  @After
  public void tearDown() throws Exception
  {
  }

  @Ignore
  @Test
  public void testExportToLocal()
  {
    String vmName = "Win7-Ult-SP1-x86_TEMPLATE-NIS-ESD-20-0-0-39.exe-by-6f60a72";
    boolean status = OVFOperation.exportToLocal(vmName, null);
    logger.info("status is: " + status);

  }

}
