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

import com.lebk.dao.ProductDao;
import com.lebk.dao.PtColorDao;
import com.lebk.dao.PtDetailsDao;
import com.lebk.dao.PtSizeDao;
import com.lebk.dao.PtTypeDao;
import com.lebk.dao.impl.ProductDaoImpl;
import com.lebk.dao.impl.PtColorDaoImpl;
import com.lebk.dao.impl.PtDetailsDaoImpl;
import com.lebk.dao.impl.PtSizeDaoImpl;
import com.lebk.dao.impl.PtTypeDaoImpl;
import com.lebk.enumType.BusinessEnumType;
import com.lebk.po.Product;

/**
 * Copyright: All Right Reserved.
 * 
 * @author Lei Bo(lebk.lei@gmail.com)
 * @contact: qq 87535204
 * @date 2013-10-24
 */

public class ProductDaoTest
{
  static Logger logger = Logger.getLogger(ProductDaoTest.class);
  ProductDao pd;
  Integer pNum = TestUtil.getPNum();
  Integer ptSizeId;
  Integer ptTypeId;
  Integer ptColorId;
  PtDetailsDao pdd = new PtDetailsDaoImpl();
  String pName;

  @Before
  public void setUp() throws Exception
  {
    pd = new ProductDaoImpl();
    String ptType = TestUtil.getPtTypeName();
    String ptSize = TestUtil.getPtSizeName();
    String ptColor = TestUtil.getPtColorName();
    Integer opUserId = TestUtil.getOpUserId();
    pName = ptType + "-" + ptSize + "-" + ptColor;
    PtSizeDao psd = new PtSizeDaoImpl();
    psd.addPtSize(ptSize, TestUtil.getOpUserId());
    ptSizeId = psd.getIdByPtSizeName(ptSize);

    PtTypeDao ptd = new PtTypeDaoImpl();
    ptd.addPtType(ptType, opUserId);
    ptTypeId = ptd.getIdByPtType(ptType);

    PtColorDao pcd = new PtColorDaoImpl();
    pcd.addPtColor(ptColor, TestUtil.getOpUserId());
    ptColorId = pcd.getIdByPtColorName(ptColor);

  }

  @After
  public void tearDown() throws Exception
  {
    pd = null;

  }

  @Ignore
  @Test
  public void testGetProductList()
  {
    List<Product> pdl = pd.getProductList();
    for (Iterator it = pdl.iterator(); it.hasNext();)
    {
      logger.info(it.next());
    }
    Assert.assertTrue("There should be more than 0 in the product list", pdl.size() > 0);
  }

  @Ignore
  @Test
  public void testGetProdTypebyProdTypeId()
  {
    fail("Not yet implemented");
  }

  @Ignore
  @Test
  public void testGetNameByProductId()
  {
    Integer poId = pd.getIdByProdName(pName);
    String pName = pd.getNameByProductId(poId);
    logger.info("The product name of poId:" + poId + " is:" + pName);
    Assert.assertTrue("The product name should not be null", pName != null);
  }

  @Ignore
  @Test
  public void testGetProductByPoId()
  {
    Integer poId = pd.getIdByProdName(pName);
    Product p = pd.getProductById(poId);
    logger.info("The product name of poId:" + poId + " is:" + p.getName());
    Assert.assertTrue("The product should not be null", p != null);
  }

  @Ignore
  @Test
  public void testUpdateProductName()
  {
    fail("Not yet implemented");
  }

  @Ignore
  @Test
  public void testRemoveProduct()
  {
    Integer poId = TestUtil.getPoId();
    logger.info("The poId is:" + poId);
    Boolean status = pd.removeProduct(poId);
    Assert.assertTrue("Expect return true", status == true);
  }

  @Test
  public void testUpdateProduct()
  {

    String pName = TestUtil.getPName();
    // String pName = TestUtil.getRandString(8);
    Integer opUserId = 1;
    // in
    Integer btId = BusinessEnumType.getIdByBusinessType(BusinessEnumType.in);
    Boolean status = pd.updateProduct(pName, ptTypeId, ptColorId, ptSizeId, 100, btId, opUserId);

    Assert.assertTrue("expect return true while ship in", status == true);
    // out
    btId = BusinessEnumType.getIdByBusinessType(BusinessEnumType.out);
    status = pd.updateProduct(pName, ptTypeId, ptColorId, ptSizeId, 50, btId, opUserId);
    Assert.assertTrue("expect return true while ship out", status == true);

    // ship more product than we have

    Integer pNumInStore = pd.getProductById(pd.getIdByProdName(pName)).getPtNumber();
    Integer ptdNumInStore = pdd.getAllPtDetails().size();
    logger.info("The pNumInStore is:" + pNumInStore);
    // btId is ship out
    status = pd.updateProduct(pName, ptTypeId, ptColorId, ptSizeId, pNumInStore + 1, btId, opUserId);
    Assert.assertTrue("expect return false while ship out more product than we have in store", status == false);
    Integer pNumInStoreAfterShipFail = pd.getProductById(pd.getIdByProdName(pName)).getPtNumber();
    logger.info("The pNumInStoreAfterShipFail is:" + pNumInStoreAfterShipFail);
    Assert.assertTrue("expect the product number will not change after a failed ship out", pNumInStoreAfterShipFail.equals(pNumInStore));
    Integer ptdNumInStoreAfterShipFail = pdd.getAllPtDetails().size();
    Assert.assertTrue("expect the product detail number will not change after a failed ship out", ptdNumInStoreAfterShipFail == ptdNumInStore);
  }
}
