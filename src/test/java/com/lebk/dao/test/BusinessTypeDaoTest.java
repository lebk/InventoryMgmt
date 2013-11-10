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

import com.lebk.dao.BusinessTypeDao;
import com.lebk.dao.impl.BusinessTypeDaoImpl;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-10-19
 */

public class BusinessTypeDaoTest {

	static Logger logger = Logger.getLogger(BusinessTypeDaoTest.class);

	BusinessTypeDao btd;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		btd = new BusinessTypeDaoImpl();
	}

	@After
	public void tearDown() throws Exception {
		btd = null;
	}

	@Test
	public void testGetAllBusinessType() {
		List btl = btd.getAllBusinessType();
		Assert.assertTrue("There should be just two types of business type",
				btl.size() == 2);
		for (Iterator it = btl.iterator(); it.hasNext();) {
			logger.info(it.next().toString());
		}
	}

}
