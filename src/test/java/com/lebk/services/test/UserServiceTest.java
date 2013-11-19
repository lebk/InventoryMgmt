package com.lebk.services.test;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.lebk.dao.test.TestUtil;
import com.lebk.enumType.UserEnumType;
import com.lebk.services.UserService;
import com.lebk.services.impl.UserServiceImpl;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-11-19
 */

public class UserServiceTest
{
  static Logger logger = Logger.getLogger(UserServiceTest.class);

  UserService us = new UserServiceImpl();

  @Before
  public void setUp() throws Exception
  {
  }

  @After
  public void tearDown() throws Exception
  {
  }

  @Test
  public void testAuthUser()
  {
  }

  @Test
  public void testAddUser()
  {
    String name = TestUtil.getRandString(6);
    String password = "123456";
    Integer type = UserEnumType.getUserTypeId(UserEnumType.admin);
    String email = "";
    String opUser = "管理员";
    Boolean status = us.addUser(name, password, type, null, opUser);
    Assert.assertTrue("expect return true", status == true);
  }

  @Test
  public void testGetUserList()
  {
  }

}
