package com.leikai.dao.test;

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

import com.leikai.dao.ProductTypeDao;
import com.leikai.dao.impl.ProductTypeDaoImpl;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-10-19
 */

public class ProductTypeDaoTest {

	static Logger logger = Logger.getLogger(ProductTypeDaoTest.class);
	ProductTypeDao ptd;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		ptd = new ProductTypeDaoImpl();

	}

	@After
	public void tearDown() throws Exception {
		ptd = null;

	}

	@Test
	public void testAddProductType() {

	}

	@Test
	public void testDeleteProductTpe() {
	}

	@Test
	public void testGetAllProductType() {
		List ptdl = ptd.getAllProductType();
		Assert.assertTrue("We have 3 types of product currently",
				ptdl.size() == 3);
		for (Iterator it = ptdl.iterator(); it.hasNext();) {
			logger.info(it.next());
		}
	}

	@Test
	public void testGetIdByProductType() {
	}

	@Test
	public void testGetNameByPtTypeId() {
	}

	@Test
	public void testGetProductTypeByPtTypeId() {
	}

	@Test
	public void testIsPtTypeExisted() {
	}

}
