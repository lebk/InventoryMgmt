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
import org.junit.Ignore;
import org.junit.Test;

import com.lebk.dao.UserDao;
import com.lebk.dao.impl.UserDaoImpl;
import com.lebk.po.User;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-10-19
 */

public class UserDaoTest {

	static Logger logger = Logger.getLogger(UserDaoTest.class);
	UserDao ud;
	private String name = "testuser";
	private String password = "testpassword";
	private Integer type = 2;
	private String email = "testemail@gmail.com";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logger.info("setUpBeforeClass");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		logger.info("tearDownAfterClass");

	}

	@Before
	public void setUp() throws Exception {
		ud = new UserDaoImpl();
	}

	@After
	public void tearDown() throws Exception {
		ud = null;
	}

	@Test
	public void testAddUser() {
		logger.info("testAddUser");
		if (ud.isUserValid(name) == false) {
			Boolean status = ud.addUser(name, password, type, email);
			Assert.assertTrue("should be success, add status:" + status, status);
		} else {
			Assert.assertTrue("User already existed, return true", true);
		}
	}

	@Test
	public void testGetUserList() {
		logger.info("testGetUserList");
		List<User> ul = ud.getUserList();
		Iterator it = ul.iterator();
		while (it.hasNext()) {
			User u = (User) it.next();
			logger.info("The name is:" + u.getName());
		}
		Assert.assertTrue("The list should be greater than 0", ul.size() > 0);
	}

	@Test
	public void testDeleteUser() {
		logger.info("testGetUserList");
		ud.addUser(name, password, type, email);
		Boolean status = ud.deleteUser(name);
		Assert.assertTrue("should be success, delete status:" + status, status);
		Boolean isValid = ud.isUserValid(name);
		Assert.assertTrue("Valid should be false after delete:" + isValid,
				isValid == false);

	}

	@Ignore
	@Test
	public void testUpdateUserType() {
		fail("Not yet implemented");
	}

	@Test
	public void testAuthUser() {
		logger.info("testAuthUser");
		ud.addUser(name, password, type, email);
		Boolean status = ud.authUser(name, password);
		Assert.assertTrue("should be success, auth status:" + status, status);

		status = ud.authUser(name + "_invalid", password);
		Assert.assertTrue(
				"should be failed with invalid username, auth status:" + status,
				status == false);

		status = ud.authUser(name, password + "_invalid");
		Assert.assertTrue(
				"should be failed with invalid password, auth status:" + status,
				status == false);

		status = ud.authUser(name + "_invalid", password + "_invalid");
		Assert.assertTrue(
				"should be failed with invalid username/password, auth status:"
						+ status, status == false);

	}

}
