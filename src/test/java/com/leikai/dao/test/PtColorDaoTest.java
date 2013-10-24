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
import com.leikai.po.Ptcolor;

public class PtColorDaoTest {

	static Logger logger = Logger.getLogger(PtColorDaoTest.class);
	PtColorDao pcd;
	String ptcolorName = "测试颜色";

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
		Boolean status = pcd.deletePtColor(ptcolorName);
		status = pcd.addPtColor(ptcolorName);
		Assert.assertTrue("Add color successfully", status);
		status = pcd.addPtColor(ptcolorName);
		Assert.assertTrue("Add color should be failed as it already existed",
				status == false);
	}

	@Test
	public void testDeletePtColor() {

		Boolean status = pcd.deletePtColor(ptcolorName);
		Assert.assertTrue("delete color successfully", status);
	}

	@Test
	public void testGetIdByPtColorName() {
		Integer id = pcd.getIdByPtColorName(ptcolorName);
		logger.info("The id is:" + id);
		Assert.assertTrue("The id should be greater than 0", id > 0);

	}

	@Test
	public void testGetColorNameByPtColorId() {
		Integer id = pcd.getIdByPtColorName(ptcolorName);
		String name = pcd.getColorNameByPtColorId(id);
		logger.info("The name is:" + name);
		Assert.assertTrue("The name should not be null", name != null);
	}

	@Test
	public void testGetPtColorByPtcolorId() {
		Integer id = pcd.getIdByPtColorName(ptcolorName);
		Ptcolor pc = pcd.getPtColorByPtcolorId(id);
		logger.info("The pc color is:" + pc.getColor());
		Assert.assertTrue("The pc color should not be null", pc != null);

	}

	@Test
	public void testIsPtColorExisted() {
		pcd.deletePtColor(ptcolorName);
		Boolean status = pcd.isPtColorExisted(ptcolorName);
		Assert.assertTrue("The color should not be existed after deleting",
				status == false);

		pcd.addPtColor(ptcolorName);
		status = pcd.isPtColorExisted(ptcolorName);
		Assert.assertTrue("The color should be existed after adding",
				status == true);
	}

}
