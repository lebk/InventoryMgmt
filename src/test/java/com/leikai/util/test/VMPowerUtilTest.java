package com.leikai.util.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.leikai.util.VMPowerUtil;

/**
 * copyright: all right reserved.
 * 
 * Author: Lei Bo
 *
 * 2013-10-9
 *
 */
public class VMPowerUtilTest
{

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
  public void testPowerOn()
  {
    Assert.assertTrue("Should return false", VMPowerUtil.powerOnTillAvail("WinXp32"));

    Assert.assertFalse("Should return false", VMPowerUtil.powerOnTillAvail("Win7-x86-baseimage-notefound"));

  }

  @Ignore
  @Test
  public void testShutdown()
  {
    VMPowerUtil.powerOnTillAvail("Win7-x86-baseimage");

    VMPowerUtil.shutdown("Win7-x86-baseimage");
  }

}
