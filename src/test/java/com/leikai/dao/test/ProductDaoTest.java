package com.leikai.dao.test;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.leikai.dao.ProductDao;
import com.leikai.dao.PtTypeDao;
import com.leikai.dao.impl.ProductDaoImpl;
import com.leikai.po.Product;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-10-24
 */

public class ProductDaoTest {
	static Logger logger = Logger.getLogger(ProductDaoTest.class);
	ProductDao pd;
	String pName;
	String pType;
	String pSize;
	String pColor;
	Integer pNum;

	@Before
	public void setUp() throws Exception {
		pd = new ProductDaoImpl();

	}

	@After
	public void tearDown() throws Exception {
		pd = null;
	}

	@Test
	public void testGetProductList() {
		List<Product> pdl = pd.getProductList();
		for (Iterator it = pdl.iterator(); it.hasNext();) {
			logger.info(it.next());
		}
		Assert.assertTrue("There should be more than 0 in the product list",
				pdl.size() > 0);
	}

	@Test
	public void testAddProduct() {
		Boolean status = pd.addProduct(pName, pType, pColor, pSize, pNum);

		Assert.assertTrue("Should add product successfully", status == true);
	}

	@Test
	public void testReduceProduct() {
		Boolean status = pd.addProduct(pName, pType, pColor, pSize, pNum);

		Assert.assertTrue("Should add product successfully", status == true);
		Integer poId = pd.getIdByProdName(pName);
		status = pd.reduceProduct(poId, pNum);
		Assert.assertTrue("Should add product successfully", status == true);

	}

	@Test
	public void testGetIdByProdName() {
		Integer poId = pd.getIdByProdName(pName);
		logger.info("The product id for " + pName + " is:" + poId);
		Assert.assertTrue("The product id should be greater than 0", poId > 0);

	}

	@Test
	public void testGetProdTypebyProdTypeId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNameByProductId() {
		Integer poId = pd.getIdByProdName(pName);
		String pName = pd.getNameByProductId(poId);
		logger.info("The product name of poId:" + poId + " is:" + pName);
		Assert.assertTrue("The product name should not be null", pName != null);
	}

	@Test
	public void testGetProductByPoId() {
		Integer poId = pd.getIdByProdName(pName);
		Product p = pd.getProductByPoId(poId);
		logger.info("The product name of poId:" + poId + " is:" + p.getName());
		Assert.assertTrue("The product should not be null", p != null);
	}

	@Test
	public void testUpdateProductName() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsProductExisted() {
		pd.addProduct(pName, pType, pColor, pSize, pNum);
		Boolean status = pd.isProductExisted(pName);
		Assert.assertTrue("The product should be existed", status);
	}

}
