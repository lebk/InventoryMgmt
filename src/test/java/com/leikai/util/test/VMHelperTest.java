package com.leikai.util.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.leikai.util.HibernateUtil;
import com.leikai.util.VMFactoryConfigUtil;
import com.leikai.util.VMHelper;
import com.vmware.vim25.FileInfo;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class VMHelperTest
{

  private static Logger logger = Logger.getLogger(VMHelperTest.class);
  private static String dsName = VMFactoryConfigUtil.getDatastoreName();

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

  @Test
  public void testConstructArgs()
  {
  }

  @Ignore
  @Test
  public void testGetFilesInVM()
  {
    List<FileInfo> fil = VMHelper.getFilesInVM(dsName, "CentOS_6.2_x86_64cloned");

    for (FileInfo fi : fil)
    {
      logger.info("file name is: " + fi.getPath());
    }
    Assert.assertTrue(fil.size() > 0);
  }

  @Ignore
  @Test
  public void testGetVmxName()
  {

    String vmxFile = VMHelper.getVmxName(dsName, "CentOS_6.2_x86_64cloned");
    logger.info(vmxFile);
    Assert.assertTrue("The vmx name should be: Win7-x86-baseimage.vmx", vmxFile.equals("CentOS_6.2_x86_64.vmx"));
  }

}
