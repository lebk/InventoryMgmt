package com.leikai.dao.test;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.leikai.dao.PtColorDao;
import com.leikai.dao.impl.PtColorDaoImpl;

public class PtColorDaoImplTest {

	static Logger logger = Logger.getLogger(PtColorDaoImplTest.class);
	PtColorDao pcd;
	String ptColor = "测试颜色";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		pcd = new PtColorDaoImpl();
	}

	@After
	public void tearDown() throws Exception {
		pcd = null;
	}

	@Test
	public void testAddPtColor() {
		Boolean status = pcd.deletePtColor(ptColor);
		status = pcd.addPtColor(ptColor);
		Assert.assertTrue("Add color successfully", status);
	}

	@Test
	public void testDeletePtColor() {

		Boolean status = pcd.deletePtColor(ptColor);
		Assert.assertTrue("delete color successfully", status);
	}

	@Test
	public void testGetIdByPtColorName() {

	}

	@Test
	public void testGetColorNameByPtColorId() {

	}

	@Test
	public void testGetPtColorByPtcolorId() {

	}

	@Test
	public void testIsPtColorExisted() {

	}

}
